import './App.css';
import { BrowserRouter as Router, Link, Routes, Route } from 'react-router-dom';
import Join from './Join';
import Login from './Login';
import JobPosting from "./JobPosting";
import PostJobs from "./PostJobs";
import {useEffect, useState} from "react";
import UserInfo from "./userInfo";

function App() {
    const [usersId, setUsersId] = useState();

    useEffect(()=>{
        setUsersId(UserInfo);
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
                <Route path='/join' Component={Join}/>
                <Route path='/login' Component={Login}/>
                <Route path='/jobPosting' Component={JobPosting}/>
                <Route path='/' Component={JobPosting}/>
                <Route path='/postJobs' element={<PostJobs users={usersId}/>}/>
            </Routes>
        </div>
    </Router>
  );
}

export default App;
