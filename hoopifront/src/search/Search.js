import './Search.css'

const Search = () => {
    
    return(
        <div className= 'search-container'>
            <div className= 'search-input-box'>
                <select className= 'search-input-box'>
                    <option>상품명</option>
                    <option>시리즈명</option>
                </select>
                <input placeholder='검색어를 입력하세요'/>
                <button>검색</button>
            </div>
        </div>
    );
}
export default Search;