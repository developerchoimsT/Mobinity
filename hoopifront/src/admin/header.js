import {useLocation} from "react-router-dom";
import {useEffect, useState} from "react";
import './header.css';

const Header = () => {
    const locatoin = useLocation();
    const path = locatoin.pathname;
    const [boardName, setBoardName] = useState();

    useEffect(() => {
        fetchPath(path)
    }, [path])

    const fetchPath = (path) => {
        if(path.includes('main')||path.includes('user')){
            setBoardName('회원')
        } else if(path.includes('order')){
            setBoardName('주문')
        } else if(path.includes('board')){
            setBoardName('게시글 및 댓글')
        } else if(path.includes('product')){
            setBoardName('상품')
        } else if(path.includes('notice')){
            setBoardName('공지')
        }
    }
    return(
      <div className='admin-header-container'>
          <table>
              <thead>
              <tr>
                  <td>
                      {boardName} 관리 게시판
                  </td>
                  <td>
                      {path.includes('admin/notice')?<button>글쓰기</button>
                          : path.includes('admin/product/write')?<button>상품 추가</button>
                              :''}
                  </td>
              </tr>
              </thead>
          </table>
      </div>
    );

}

export default Header;