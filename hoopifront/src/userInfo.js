import axios from "axios";

const UserInfo = () =>{
    try {
        const response = axios.get("http://hoopi.p-e.kr/api/hoopi/userInfo");
        console.log(response.data);
        return response.data;
    } catch(error) {
        console.log("유저 정보를 불러오지 못했습니다.", error);
    }

}
export default UserInfo;
