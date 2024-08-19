import { useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import {UserContext} from "./App";

const Login = () => {
    const [users, setUsers] = useState({ 'usersId': '', 'usersPw': '' });
    const navigate = useNavigate();
    const { userInfo, setUserInfo } = useContext(UserContext);

    const handleUsers = (e) => {
        switch(e.target.id) {
            case 'id':
                setUsers(prevUsers => ({
                    ...prevUsers,
                    'usersId': e.target.value
                }));
                break;
            case 'pwd':
                setUsers(prevUsers => ({
                    ...prevUsers,
                    'usersPw': e.target.value
                }));
                break;
            default:
                break;
        }
    }


    const handleLogin = async () => {
        if (users.usersId === '' || users.usersPw === '') {
            alert('아이디와 비밀번호를 모두 입력해주세요.');
            return;
        }

        try {
            const response = await axios.post("http://hoopi.p-e.kr/api/hoopi/login", users);
            handleUserInfo(response.data);
            console.log(userInfo);
        } catch (error) {
            console.error('로그인 요청 오류:', error);
            alert('서버 오류 발생. 나중에 다시 시도해 주세요.');
        }
    }

    const handleUserInfo = (e) => {
        setUserInfo({
            'usersId' : e.usersId,
            'usersRole' : e.usersRole
        })
    }

    return (
        <div>
            <table>
                <thead>
                    <tr>
                        <th colSpan={6}>로그인</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>아이디</td>
                        <td colSpan={3}>
                            <input id="id" onChange={handleUsers} value={users.usersId} />
                        </td>
                        <td rowSpan={2}>
                            <button onClick={handleLogin}>로그인</button>
                        </td>
                    </tr>
                    <tr>
                        <td>비밀번호</td>
                        <td colSpan={3}>
                            <input type="password" id="pwd" onChange={handleUsers} value={users.usersPw} />
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    )
}

export default Login;