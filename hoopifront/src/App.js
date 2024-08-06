import './App.css';
import { BrowserRouter as Router, Link, Routes, Route } from 'react-router-dom';
import Join from './Join';
import Login from './Login';
import JobPosting from "./JobPosting";
import PostJobs from "./PostJobs";
import {useEffect, useState} from "react";
import axios from "axios";
// Context 생성
const UserContext = createContext(null);

// 컨텍스트 프로바이더 컴포넌트
function UserProvider({ children }) {
    const [userId, setUserId] = useState('');

    useEffect(() => {
        async function fetchUserInfo() {
            try {
                const response = await axios.get('http://hoopi.p-e.kr/api/hoopi/userInfo');
                if (response.data !== "" && response.data != null) {
                    console.log(response.data);
                    setUserId(response.data);
                }
            } catch (error) {
                console.error("유저 정보를 불러오지 못했습니다.", error);
            }
        }

        fetchUserInfo();
    }, []);

    return (
        <UserContext.Provider value={{ userId, setUserId }}>
            {children}
        </UserContext.Provider>
    );
}

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
                  </Routes>
              </div>
          </Router>
      </UserProvider>
  );
}

export default App;
