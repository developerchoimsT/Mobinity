import Header from "./header";
import UserBody from "./user/userBody";
import {useEffect} from "react";
import {useLocation} from "react-router-dom";
import Board from "./board/Board";

const AdminMain = () => {
    const id = localStorage.getItem("id");
    const role = localStorage.getItem("role");

    const location = useLocation();
    const path = location.pathname;

    useEffect(() => {
        fetchAdmin();
    }, []);

    const fetchAdmin = () => {
        if(id == null || id == '' || role == null || role == '' || role == 'user'){
            alert("관리자만 접근가능합니다.");
            window.location.href = '/';
        }
    }

    return(
        <div>
            <Header/>
            {path.includes('admin/user')||path.includes('admin/main')?<UserBody/>
            :path.includes('admin')&&path.includes('write')?<Board/>
            :<UserBody/>}
        </div>
    );
}

export default AdminMain