import { useEffect, useState } from "react";
import axios from "axios";
import Pagination from "@mui/material/Pagination";
import Stack from "@mui/material/Stack";

const UserBody = () => {
    const id = localStorage.getItem("id");
    const role = localStorage.getItem("role");

    const [userPage, setUserPage] = useState({ content: [], totalPages: 0 });
    const [currentPage, setCurrentPage] = useState(1);
    const [userDetail, setUserDetail] = useState(null);
    const [detailVisible, setDetailVisible] = useState(false);

    useEffect(() => {
        fetchUsers(currentPage);
    }, [currentPage]); // 페이지 변경시 자동으로 데이터 로딩

    const fetchUsers = async (page) => {
        try {
            const response = await axios.get("http://hoopi.p-e.kr/api/hoopi/admin/user", {
                params: { searchCate: "id", keyword: id, page: page - 1, size: 10 }
            });
            console.log("response.data : ", response.data.content);
            setUserPage(response.data);
        } catch (error) {
            console.error("Failed to fetch data:", error);
            alert("데이터를 불러오는데 실패했습니다.");
        }
    };

    const handleUserDetail = async (userId) => {
        try {
            const response = await axios.get(`http://hoopi.p-e.kr/api/hoopi/admin/user-detail/${userId}`);
            setUserDetail(response.data);
            setDetailVisible(true);
        } catch (error) {
            alert("상세 정보를 불러오는데 실패했습니다.");
        }
    };

    const handleUserQuit = async (userId) => {
        try {
            const response = await axios.delete(`http://hoopi.p-e.kr/api/hoopi/admin/user-quit/${userId}`);
            alert("사용자 탈퇴 처리가 완료되었습니다.");
            fetchUsers(currentPage); // 상태 업데이트 후 목록 새로고침
        } catch (error) {
            alert("탈퇴 처리에 실패했습니다.");
        }
    };

    const handleClose = () => {
        setDetailVisible(false);
    };

    const handlePageChange = (event, page) => {
        setCurrentPage(page);
    };

    return (
        <div>
            <table>
                <thead>
                <tr>
                    <th>순서</th>
                    <th>아이디</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>핸드폰 번호</th>
                </tr>
                </thead>
                <tbody>
                {userPage.content.map((item, index) => (
                    <tr key={item.code} id={item.userId} onClick={() => handleUserDetail(item.id)}>
                        <td>{index + 1}</td>
                        <td>{item.userId}</td>
                        <td>{item.userName}</td>
                        <td>{item.email}</td>
                        <td>{item.phone}</td>
                    </tr>
                ))}
                </tbody>
            </table>
            {detailVisible && userDetail && (
                <div className='admin-user-detail-container' style={{ display: "block" }}>
                    {/* 상세 정보 테이블 */}
                </div>
            )}
            <Stack spacing={2}>
                <Pagination count={userPage.totalPages} page={currentPage} onChange={handlePageChange} variant="outlined" color="primary" />
            </Stack>
        </div>
    );
};

export default UserBody;
