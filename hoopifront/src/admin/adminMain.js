import Header from "./header";
import UserBody from "./user/userBody";
import {useEffect} from "react";

const AdminMain = () => {
    const id = localStorage.getItem("id");
    const role = localStorage.getItem("role");

    useEffect(() => {
        fetchAdmin();
    }, [id, role]);

    const fetchAdmin = () => {
        if(id == null || id == '' || role == null || role == '' || role == 'user'){
            if(error.status == 401 || error.status == 403){
                alert("관리자만 접근가능합니다.");
                window.location.href = '/';
                return;
            }
        }
    }

    return(
        <div>
            <Header/>
            <UserBody/>
        </div>
    );
}

export default AdminMain