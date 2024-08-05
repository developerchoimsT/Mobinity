import './App.css';
import { BrowserRouter as Router, Link, Routes, Route } from 'react-router-dom';
import Join from './Join';
import Login from './Login';
import JobPosting from "./JobPosting";
import PostJobs from "./PostJobs";
import {useEffect, useState} from "react";
import axios from "axios";

function App() {
    const [usersId, setUsersId] = useState();

    useEffect(() => {
        const userInfo = async () => {
            await axios.get("http://hoopi.p-e.kr/api/hoopi/userInfo")
                .then((res) => {
                    if(res.data != null){
                        setUsersId(res.data);
                    }
                })
        };
        userInfo();
    })

  return (
    
    <Router>
      <nav className='mainNav'>
          <ul>
              <li><Link to='/jobPosting'>채용 공고</Link></li>
          </ul>
          <ul>
            <li><Link to="/join">회원가입</Link></li>
            <li><Link to="/login">로그인</Link></li>
          </ul>
        </nav>
        <div className='main-box'>
            <Routes>
                <Route path='/join' element={<Join />}/>
                <Route path='/login' element={<Login />}/>
                <Route path='/jobPosting' element={<JobPosting />}/>
                <Route path='/' element={<JobPosting />}/>
                <Route path='/postJobs' element={<PostJobs usersId={usersId}/>}/>
            </Routes>
        </div>
    </Router>
  );
}

export default App;
