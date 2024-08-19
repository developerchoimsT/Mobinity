import axios from "axios";
import { useState } from "react";

function Join() {
    
    //값 저장
    const[role, setRole] = useState("");
    const[id, setId] = useState('');
    const[pw, setPw] = useState('');
    const[chkPw, setChkPw] = useState('');
    const[name, setName] = useState('');
    const[email, setEmail] = useState('');
    const[emailCd, setEmailCd] = useState('');
    const[chkEmailCd, setChkEmailCd] = useState('');
    const[phone, setPhone] = useState('');
    const[phoneCd, setPhoneCd] = useState('');
    const[chkPhoneCd, setChkPhoneCd] = useState('');
    const[users, setUsers] = useState([]);

    const handleChange = (e) => {
        switch (e.target.id) {
            case 'id':
                setId(e.target.value);
                setUsers(prevUsers => ({
                    ...prevUsers,
                    usersId: e.target.value
                }));
                break;
            case 'pw':
                setPw(e.target.value);
                setUsers(prevUsers => ({
                    ...prevUsers,
                    usersPw: e.target.value
                }));
                break;
            case 'chkPw':
                setChkPw(e.target.value);
                break;
            case 'name':
                setName(e.target.value);
                setUsers(prevUsers => ({
                    ...prevUsers,
                    usersNm: e.target.value
                }));
                break;
            case 'email':
                setEmail(e.target.value);
                setUsers(prevUsers => ({
                    ...prevUsers,
                    usersEmail: e.target.value
                }));
                break;
            case 'chkEmailCd':
                setChkEmailCd(e.target.value);
                break;
            case 'phone':
                setPhone(e.target.value);
                setUsers(prevUsers => ({
                    ...prevUsers,
                    usersPhone: e.target.value
                }));
                break;
            case 'chkPhoneCd':
                setChkPhoneCd(e.target.value);
                break;
            case 'role' :
                if(e.target.checked) {
                    setRole('company');
                    setUsers(prevUsers => ({
                        ...prevUsers,
                        usersRole: 'company'
                    }))
                }
        }
    }
    
    //인증 버튼 눌러 본인인증 요청하기
    const handleEmail = () => {
        if(email == "" || email == null) {
            alert("이메일을 입력해주세요");
            return false;
        }
        
        axios.post("http://hoopi.p-e.kr/api/hoopi/email", null, {
                params: {
                   email: email
                }
            })
            .then(response => {
                setEmailCd(response.data);
                alert("이메일을 확인하여 본인인증을 완료해주세요.");
            })
            .catch(error => {
                alert(error);
                setEmailCd('');
                setEmail('');
            })

    }

    //인증 완료하기
    const chkEmail = () => {
        if(emailCd == chkEmailCd){
            alert("이메일 인증에 성공하셨습니다.");
        } else {
            alert("다시 인증해주세요.");
            setEmailCd('');
            setEmail('');
        }
    }

    //핸드폰 인증하기
    const handlePhone = () => {
        axios.post("http://hoopi.p-e.kr/api/hoopi/phone", null ,{
            params: {
                phone: phone
            }
        })
            .then(response => {
                setPhoneCd(response.data);
                alert("핸드폰을 확인하여 본인인증을 완료해주세요.");
            })
            .catch(error => {
                alert(error);
                setPhoneCd('');
                setPhoneCd('');
                setChkPhoneCd('');
            })
    }

    //핸드폰 인증 완료하기
    const chkPhone = () => {
        if(phoneCd == chkPhoneCd){
            alert("핸드폰 인증에 성공하셨습니다.");
        } else {
            alert("다시 인증해주세요.");
            setPhoneCd('');
            setPhoneCd('');
            setChkPhoneCd('');
        }
    }

    //회원가입 신청
    const handleJoin = () => {
        axios.post("http://hoopi.p-e.kr/api/hoopi/join", users)
        .then(response => {
            if(response.status != 200){
                alert("다시 시도해주세요.");
            } else {
                alert("회원가입이 성공적으로 이루어졌습니다.");
            }
        })
    }


    return (
        <div>
            <table>
                <thead>
                    <tr>
                        <td colSpan = {6}>회원 가입</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>기업 회원인가요?</tr>
                        <td colSpan={5}><input id="role" type="radio" onChange={handleChange}/></td>
                    <tr>
                        <td>ID</td>
                        <td colSpan={5}><input id="id" value={id} onChange={handleChange}></input></td>
                    </tr>
                    <tr>
                        <td>password</td>
                        <td colSpan={5}><input id="pw" value={pw} onChange={handleChange}></input></td>
                    </tr>
                    <tr>
                        <td>비밀번호 확인</td>
                        <td colSpan={5}><input id="chkPw" value={chkPw} onChange={handleChange}></input></td>
                    </tr>
                    <tr>
                        <td>이름</td>
                        <td colSpan={5}><input id="name" value={name} onChange={handleChange}></input></td>
                    </tr>
                    <tr>
                        <td>이메일</td>
                        <td colSpan={2}><input id="email" value={email} onChange={handleChange}></input></td>
                        <td><button id="emailChkBtn" onClick={handleEmail}>인증</button></td>
                        <td colSpan={2}><input id="chkEmailCd" value={chkEmailCd} onChange={handleChange}></input></td>
                        <td><button onClick={chkEmail}>이메일인증</button></td>
                    </tr>
                    <tr>
                        <td>핸드폰</td>
                        <td colSpan={2}><input id="phone" value={phone} onChange={handleChange}></input></td>
                        <td><button id="phoneChkBtn" onClick={handlePhone}>인증</button></td>
                        <td colSpan={2}><input id="chkPhoneCd" value={chkPhoneCd} onChange={handleChange}></input></td>
                        <td><button onClick={chkPhone}>핸드폰
                            인증</button></td>
                    </tr>
                    <tr>
                        <td colSpan={6}><button onClick={handleJoin}>회원가입</button></td>
                    </tr>
                </tbody>
            </table>
        </div>
    );
}

export default Join;