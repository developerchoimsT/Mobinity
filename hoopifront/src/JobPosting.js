import {useEffect, useState} from "react";
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";
import './JobPosting.css';

const JobPosting = () => {

    const [jobPostings, setJobPostings] = useState([]);
    const [search, setSearch] = useState("");

    useEffect(()=>{
        const fetchJobPosting = async () => {
            try {
                const response = await axios.get("http://hoopi.p-e.kr/api/hoopi/job", {
                                                                    params: { search: search }
                                                                });
                setJobPostings(response.data);
                console.log(response.data);
            } catch (error) {
                console.log("채용 공고를 불러오지 못했습니다.", error);
            }
        };
        fetchJobPosting();
    }, [search])

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
                <button><Link to='/postJobs'>채용 공고 올리기</Link></button>
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
                            <th>지원</th>
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
                                            <td>{job.company.companyName}</td>
                                            <td>{job.company.companyNation}</td>
                                            <td>{job.company.companyLocation}</td>
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