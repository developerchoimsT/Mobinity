import {useEffect} from "react";

const JobPostingDetail = () => {

    useEffect(()=>{
        const fetchDetail = async () => {

        }
    })
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
                        <td></td>
                    </tr>
                    <tr>
                        <th>회사명</th>
                        <td></td>
                    </tr>
                    <tr>
                        <th>국가</th>
                        <td></td>
                    </tr>
                    <tr>
                        <th>지역</th>
                        <td></td>
                    </tr>
                    <tr>
                        <th>채용포지션</th>
                        <td></td>
                    </tr>
                    <tr>
                        <th>채용보상금</th>
                        <td></td>
                    </tr>
                    <tr>
                        <th>사용기술</th>
                        <td></td>
                    </tr>
                    <tr>
                        <th>채용내용</th>
                        <td></td>
                    </tr>
                    <tr>
                        <th>회사가 올린 다른 채용 공고</th>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </div>
    );
}

export default JobPostingDetail;