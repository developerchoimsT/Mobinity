import {useContext, useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import {UserContext} from "./App";
import jobPosting from "./JobPosting";

const JobPostingDetail = () => {

    const {jobPostingCd} = useParams();
    const {userInfo} = useContext(UserContext);
    const navigate = useNavigate();
    const [jobPostingDto, setJobPostingDto] = useState('');
    const [companyDto, setCompanyDto] = useState('');
    const [applyDisable, setApplyDisable] = useState(false);
    const [editDisable, setEditDisable] = useState(false);
    const [deleteDisable, setDeleteDisable] = useState(false);

    useEffect(() => {
        const fetchDetail = async () => {
            try {
                const response = await axios.get('http://hoopi.p-e.kr/api/hoopi/jobDetail', {
                    params: { jobPostingCd: jobPostingCd }
                });
                setJobPostingDto(response.data.jobPostingDto);
                setCompanyDto(response.data.companyDto);
            } catch (error) {
                console.log(error);
            }
        };

        fetchDetail();
    }, [jobPostingCd]);

    useEffect(() => {
        console.log("회사 정보",companyDto.companyName);
        console.log("회원 정보", userInfo.usersId);
        const fetchButton = async () => {
            try {
                if (userInfo.usersRole === 'COMPANY') {
                    setApplyDisable(true);
                }
                if (userInfo.usersId !== companyDto.companyName) {
                    setEditDisable(true);
                    setDeleteDisable(true);
                }
            } catch (error) {
                console.log(error);
            }
        };

        fetchButton();
    }, [userInfo, companyDto]);

    const handleEdit = () => {
        navigate(`/postJobs`, { state: { jobPostingDto } });
    };

    const submitApply = () => {
        axios.post("http://hoopi.p-e.kr/api/hoopi/apply", {
                jobPostingCd: jobPostingCd,
                usersId: userInfo.usersId
            }).then( response => {
                alert(response.data);
            }).catch(error => {
                console.log(error);
        })
    };

    const handleDelete = () => {
        axios.delete("http://hoopi.p-e.kr/api/hoopi/jobDetail", {
            data: jobPostingDto
            }).then(response => {
                alert(response.data);
                navigate('/');
            }).catch(error => {
                console.log(error);
            })
    }

    return(
        <div>
            <table>
                <thead>
                    <tr>
                        <th colSpan={2}>채용 정보</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>채용공고 id</th>
                        <td>{jobPostingDto.jobPostingCd}</td>
                    </tr>
                    <tr>
                        <th>회사명</th>
                        <td>{companyDto.companyName}</td>
                    </tr>
                    <tr>
                        <th>국가</th>
                        <td>{companyDto.companyNation}</td>
                    </tr>
                    <tr>
                        <th>지역</th>
                        <td>{companyDto.companyLocation}</td>
                    </tr>
                    <tr>
                        <th>채용포지션</th>
                        <td>{jobPostingDto.jobPostingPosition}</td>
                    </tr>
                    <tr>
                        <th>채용보상금</th>
                        <td>{jobPostingDto.jobPostingMoney}</td>
                    </tr>
                    <tr>
                        <th>사용기술</th>
                        <td>{jobPostingDto.jobPostingSkill}</td>
                    </tr>
                    <tr>
                        <th>채용내용</th>
                        <td>{jobPostingDto.jobPostingBody}</td>
                    </tr>
                </tbody>
                <tfoot>
                <tr>
                    <th colSpan={2}>
                        <button id='apply' onClick={submitApply} disabled={applyDisable}>지원</button>
                    </th>
                </tr>
                <tr>
                    <th colSpan={2}>
                        <button id='edit' onClick={handleEdit} disabled={editDisable}>수정</button>
                    </th>
                </tr>
                <tr>
                    <th colSpan={2}>
                        <button id='delete' onClick={handleDelete} disabled={deleteDisable}>삭제</button>
                    </th>
                </tr>
                </tfoot>
            </table>
        </div>
    );
}

export default JobPostingDetail;