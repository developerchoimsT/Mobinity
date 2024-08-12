import {useContext, useState, useEffect} from "react";
import axios from "axios";
import {useLocation, useNavigate} from "react-router-dom";
import {UserContext} from "./App";

const PostJobs = () => {

    const {usersId, setUsersId} = useContext(UserContext);

    const [jobPosting, setJobPosting] = useState({
        companyCd: usersId,
        jobPostingPosition: "",
        jobPostingMoney: "",
        jobPostingBody: "",
        jobPostingSkill: ""
    });

    useEffect(() => {
        if (location.state) {
            const { jobPostingDto } = location.state;
            setJobPosting({
                companyCd: jobPostingDto.companyDto.companyCd,
                jobPostingCd: jobPostingDto.jobPostingCd,
                jobPostingPosition: jobPostingDto.jobPostingPosition,
                jobPostingMoney: jobPostingDto.jobPostingMoney,
                jobPostingBody: jobPostingDto.jobPostingBody,
                jobPostingSkill: jobPostingDto.jobPostingSkill
            });
        }
    }, [location, usersId]);

    const navigate = useNavigate();

    const location = useLocation();

    const postJob = (e) => {

        switch (e.target.id) {
            case "position": setJobPosting(prevState => ({
                ...prevState,
                "jobPostingPosition" : e.target.value
            }));
            break;
            case "money": setJobPosting(prevState => ({
                ...prevState,
                "jobPostingMoney" : e.target.value
            }));
            break;
            case "body": setJobPosting(prevState => ({
                ...prevState,
                "jobPostingBody" : e.target.value
            }));
                break;
            case "skill": setJobPosting(prevState => ({
                ...prevState,
                "jobPostingSkill" : e.target.value
            }));
                break;
        }
        console.log(jobPosting);
    }

    const submitJobPost = () => {
        axios.post("http://hoopi.p-e.kr/api/hoopi/job", jobPosting)
            .then(res => {
                alert(res.data);
                navigate('/');
            })
    }

    return(
        <div>
            <div>채용공고</div>
            <div>
                <table>
                    <tbody>
                    <tr>
                        <th>제안 포지션</th>
                        <td><input id='position' onChange={postJob} value={jobPosting.jobPostingPosition}/></td>
                    </tr>
                    <tr>
                        <th>취직시 보상금</th>
                        <td><input id='money' onChange={postJob} value={jobPosting.jobPostingMoney}/></td>
                    </tr>
                    <tr>
                        <th>주요 사항</th>
                        <td><input id='body' onChange={postJob} type="text" value={jobPosting.jobPostingBody}/></td>
                    </tr>
                    <tr>
                        <th>필요 기술</th>
                        <td><input id='skill' onChange={postJob} value={jobPosting.jobPostingSkill}/></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div>
                <button onClick={submitJobPost}>채용 공고 올리기</button>
            </div>
        </div>
    );
}
export default PostJobs;