import {useEffect, useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";
import './JobPosting.css';

const JobPosting = () => {

    const [jobPostings, setJobPostings] = useState([]);
    const [companies, setCompanies] = useState([]);
    const [search, setSearch] = useState("");

    useEffect(()=>{
        const fetchJobPosting = async () => {
            try {
                const response = await axios.get("http://hoopi.p-e.kr/api/hoopi/job", {
                                                                    params: { search: search }
                                                                });
                setJobPostings(response.data.jobPostings);
                setCompanies(response.data.companies);
                console.log(response.data);
            } catch (error) {
                console.log("채용 공고를 불러오지 못했습니다.", error);
            }
        };
        fetchJobPosting();
    }, [search])

    return(
        <div className="job-posting">
            <div>
                <h1>채용 공고</h1>
                <button><Link to='/postJobs'>채용 공고 올리기</Link></button>
            </div>
            <div className="posting-info">
                <table>
                    <thead>
                        <tr>
                            <th>채용 번호</th>
                            <th>채용 공고</th>
                            <th>회사 명</th>
                            <th>국가</th>
                            <th>위치</th>
                            <th>지원</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            jobPostings.map((job, index) => {
                                const company = companies.find(com => com.companyCd === job.companyCd);
                                return company ? (
                                    <tr key={index}>
                                        <td>{index + 1}</td>
                                        <td>{job.jobPostingPosition}</td>
                                        <td>{company.companyName}</td>
                                        <td>{company.companyNation}</td>
                                        <td>{company.companyLocation}</td>
                                        <td>
                                            <button>지원</button>
                                        </td>
                                    </tr>
                                ) : null;
                            })
                        }
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default JobPosting;