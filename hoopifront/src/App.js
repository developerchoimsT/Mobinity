import './App.css';
import { useState, useEffect} from 'react';
import axios from 'axios';
import { BrowserRouter as Router, Link, Routes, Route } from 'react-router-dom';
import Join from './Join';
import Login from './Login';
import Main from './Main';

function App() {

  return (
    
    <Router>
      <nav>
          <ul>
            <li><Link to="/join">회원가입</Link></li>
            <li><Link to="/login">로그인</Link></li>
          </ul>
        </nav>
      <Routes>
        <Route path='/join' Component={Join}/>
        <Route path='/login' Component={Login}/>
        <Route path='/hoopi' Component={Main}/>
      </Routes>
    </Router>
  );
}

export default App;
