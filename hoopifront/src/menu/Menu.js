import axios from "axios";
import {useEffect, useState} from "react";
import {Link, useLocation} from "react-router-dom";
import './menu.css';

const Menu = () => {
    const location = useLocation();
    const path = location.pathname;

    const role = localStorage.getItem("id");

    useEffect(() => {
        fetchMenu();
    }, [path]);

    const[visible, setVisible] = useState(false);
    const[menu, setMenu] = useState();
    const fetchMenu = async () => {
        try{
            const response = await axios.get('http://hoopi.p-e.kr/api/hoopi/menu');
            setMenu(response.data);
            if(role === 'admin'){
                setVisible(true);
            }
        } catch (error){
            console.error(error);
        }

    }
    return(
        <div className='menu-container'>
            <div className='menu-box'>
                <table>
                    <tbody>
                    <tr>
                        {menu?.map(m => (
                            <td style={{
                                display: m.name === '회원' && visible ? 'display'
                                    : m.name !== '회원' ? 'display'
                                        : 'none'
                            }} key={m.boardCode}>
                                <Link to={`${visible ? 'admin/' : ''}${m.boardId}`}>{m.name}</Link>
                            </td>
                        ))}
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default Menu;