import {useEffect, useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";
import './JobPosting.css';

const JobPosting = () => {

    const [jobPosting, setJobPosting] = useState([]);

    useEffect(()=>{
        const fetchJobPosting = async () => {
            try {
                const response = await axios.get("http://hoopi.p-e.kr/api/hoopi/job");
                setJobPosting(response.data);
            } catch (error) {
                console.log("채용 공고를 불러오지 못했습니다.", error);
            }
        };
        fetchJobPosting();
    })

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
                            <th>채용 공고</th>
                            <th>회사 명</th>
                            <th>위치</th>
                            <th>자격 요건</th>
                            <th>지원</th>
                        </tr>
                    </thead>
                    <tbody>
                        {jobPosting.map((job, index) => (
                            <tr key={index}>
                                <td>{index + 1}</td>
                                <td>{job.companyNm}</td>
                                <td>{job.location}</td>
                                <td>{job.qualification}</td>
                                <td><button>지원</button></td>
                            </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default JobPosting;