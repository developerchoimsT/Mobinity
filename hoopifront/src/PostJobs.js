import {useState} from "react";
import axios from "axios";
import cookies from "js-cookie";

const PostJobs = ({usersId}) => {

    const [jobPosting, setJobPosting] = useState({"companyCd" : usersId});

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
    }

    const submitJobPost = () => {
        axios.post("http://hoopi.p-e.kr/api/hoopi/job", jobPosting)
            .then(res => {
                alert(res.data);
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