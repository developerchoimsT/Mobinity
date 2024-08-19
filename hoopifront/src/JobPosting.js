import {useContext, useEffect, useState} from "react";
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";
import './JobPosting.css';
import {UserContext} from "./App";

const JobPosting = () => {

    const [jobPostings, setJobPostings] = useState([]);
    const [search, setSearch] = useState("");
    const {userInfo} = useContext(UserContext);
    const [writeDisable, setWriteDisable] = useState('block');

    useEffect(()=>{
        const fetchJobPosting = async () => {
            try {
                const response = await axios.get("http://hoopi.p-e.kr/api/hoopi/job", {
                                                                    params: { search: search }
                                                                });
                setJobPostings(response.data);
            } catch (error) {
                console.log("채용 공고를 불러오지 못했습니다.", error);
            }
        };

        const fetchButton = async () => {
            try{
                if(userInfo.usersRole === 'USER'){
                    setWriteDisable('none');
                }
            }catch (error) {
                console.log(error);
            }
        }
        fetchJobPosting();
        fetchButton();

    }, [search, userInfo])

    const navigate = useNavigate();
    const goDetail = (jobPostingCd) => {
        navigate(`/jobPostingDetail/${jobPostingCd}`);
    }
    const changeSearch = (e)=>{
        setSearch(e.target.value);
    }

    return(
        <div className="job-posting">
            <div>
                <h1>채용 공고</h1>
                <Link to='/postJobs' style={{display: writeDisable}}>채용 공고 올리기</Link>
            </div>
            <div>
                <input id='search' value={search} onChange={changeSearch} type='text'/>
            </div>
            <div className="posting-info">
                <table>
                    <thead>
                        <tr>
                            <th>채용 번호</th>
                            <th>채용 공고</th>
                            <th>채용 보상금</th>
                            <th>사용 기술</th>
                            <th>회사 명</th>
                            <th>국가</th>
                            <th>위치</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            jobPostings.map((job, index) => {
                                return (
                                        <tr key={job.jobPostingCd} onClick={()=>goDetail(job.jobPostingCd)}>
                                            <td>{index + 1}</td>
                                            <td>{job.jobPostingPosition}</td>
                                            <td>{job.jobPostingMoney}</td>
                                            <td>{job.jobPostingSkill}</td>
                                            <td>{job.companyDto.companyName}</td>
                                            <td>{job.companyDto.companyNation}</td>
                                            <td>{job.companyDto.companyLocation}</td>
                                        </tr>
                                );
                            })
                        }
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default JobPosting;