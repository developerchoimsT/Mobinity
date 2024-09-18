import './Search.css'
import {useEffect, useState} from "react";
import {useLocation} from "react-router-dom";
import axios from "axios";
import {useSearch} from "./SearchContext";

const Search = () => {

    const { keyword, setKeyword, searchCate, setSearchCate } = useSearch();

    const role = localStorage.getItem("role");
    const location = useLocation();
    const path = location.pathname;
    const [boardId, setBoardId] = useState('');
    const [board, setBoard] = useState('');
    const [category, setCategory] = useState([]);

    useEffect(() => {
        fetchBoard();
        fetchCategory();
    }, [path, role]);

    const fetchBoard = async () => {
        let tempBoardId = '';
        if (path.includes('user')) {
            tempBoardId = 'user';
        } else if (path.includes('order')) {
            tempBoardId = 'order';
        } else if (path.includes('product')) {
            tempBoardId = 'product';
        } else if (path.includes('notice')) {
            tempBoardId = 'notice';
        } else if (path.includes('admin/main')){
            tempBoardId = 'user';
        } else {
            tempBoardId = 'product';
        }

        try {
            console.log(tempBoardId);
            console.log(path);
            const response = await axios.get('http://hoopi.p-e.kr/api/hoopi/board', { params: { boardId: tempBoardId } });
            setBoardId(tempBoardId);  // boardId 업데이트
            setBoard(response.data);
            console.log(response.data);
        } catch (error) {
            console.error(error);
        }
    };

    const fetchCategory = async() => {
        try{
            const response = await axios.get('http://hoopi.p-e.kr/api/hoopi/category', {params: {boardCode: board.code}});
            setCategory(response.data);
        } catch (error){
            console.log(error);
        }
    }

    const handleKeyword = (e) => {
        setKeyword(e.target.value);
    }

    const handleSearchCate = (e) => {
        setSearchCate(e.target.value);
    }

    return(
            <div className= 'search-container'>
                <div className= 'search-input-box'>
                    <select className= 'search-input-box' onChange={handleSearchCate}>
                        {category.map(m => (
                            <option key={m.id} value={m.categoryId}>
                                {m.name}
                            </option>
                        ))}
                    </select>
                    <input className='keyword' value={keyword} onChange={handleKeyword} placeholder='검색어를 입력하세요'/>
                    <button>검색</button>
                </div>
            </div>
    );
}
export default Search;