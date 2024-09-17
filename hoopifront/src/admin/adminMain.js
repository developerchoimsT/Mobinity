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
            window.location.href('/login');
            return false;
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