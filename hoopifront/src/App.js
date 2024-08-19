import React, { useContext, createContext, useState, useEffect } from 'react';
import axios from 'axios';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import Login from './Login';
import Join from './Join';
import JobPosting from './JobPosting';
import PostJobs from './PostJobs';
import './App.css';
import JobPostingDetail from "./JobPostingDetail";

// Context 생성
const UserContext = createContext(null);

// 컨텍스트 프로바이더 컴포넌트
function UserProvider({ children }) {
    const [userInfo, setUserInfo] = useState({
        'usersId' : '',
        'usersRole' : ''
    });

    useEffect(() => {
        async function fetchUserInfo() {
            try {
                const response = await axios.get('http://hoopi.p-e.kr/api/hoopi/userInfo');
                if (response.data !== "" && response.data != null) {
                    setUserInfo({
                        'usersId': response.data.map.usersId,
                        'usersRole': response.data.map.usersRole
                    })
                }
            } catch (error) {
                console.error("유저 정보를 불러오지 못했습니다.", error);
            }
        }

        fetchUserInfo();
    }, []);

    return (
        <UserContext.Provider value={{ userInfo, setUserInfo }}>
            {children}
        </UserContext.Provider>
    );
}

// App 컴포넌트
function App() {
    return (
        <UserProvider>
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
                        <Route path='/postJobs' element={<PostJobs />}/>
                        <Route path='/jobPostingDetail/:jobPostingCd' element={<JobPostingDetail />}/>
                    </Routes>
                </div>
            </Router>
        </UserProvider>
    );
}

export default App;
export {UserContext};