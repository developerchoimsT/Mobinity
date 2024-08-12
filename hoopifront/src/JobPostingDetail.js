import {useContext, useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import {UserContext} from "./App";

const JobPostingDetail = () => {

    const {jobPostingCd} = useParams();
    const {usersId} = useContext(UserContext);
    const navigate = useNavigate();
    const [jobPostingDto, setJobPostingDto] = useState('');
    const [companyDto, setCompanyDto] = useState('');

    useEffect(()=>{
        const fetchDetail = async () => {
            try{
                const response = await axios.get('http://hoopi.p-e.kr/api/hoopi/jobDetail', {
                                                params : {jobPostingCd : jobPostingCd}
                                            });
                setJobPostingDto(response.data.jobPostingDto);
                setCompanyDto(response.data.companyDto);
            } catch (error){
                console.log(error);
            }
        };

        fetchDetail();
    }, [jobPostingCd])

    const handleEdit = () => {
        navigate(`/postJobs`, { state: { jobPostingDto } });
    };

    const submitApply = () => {
        axios.post("http://hoopi.p-e.kr/api/hoopi/apply", {
                jobPostingCd: jobPostingCd,
                usersId: usersId
            }).then( response => {
                alert(response.data);
            }).catch(error => {
                console.log(error);
        })
    };

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
                            <button onClick={submitApply}>지원</button>
                        </th>
                    </tr>
                    <tr>
                        <th colSpan={2}>
                            <button onClick={handleEdit}>수정</button>
                        </th>
                    </tr>
                </tfoot>
            </table>
        </div>
    );
}

export default JobPostingDetail;