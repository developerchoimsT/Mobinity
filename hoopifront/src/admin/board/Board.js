import {useLocation} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import './board.css';

const Board = () => {

    const id = localStorage.getItem("id");

    const location = useLocation();
    const path = location.pathname;

    const [board, setBoard] = useState();
    const [visible, setVisible] = useState(false);
    useEffect(() => {
        fetchBoard();
    }, [path]);

    const fetchBoard = async () => {
        let tempBoardId = '';
        if (path.includes('user')) {
            tempBoardId = 'user';
        } else if (path.includes('order')) {
            tempBoardId = 'order';
        } else if (path.includes('product')) {
            tempBoardId = 'product';
            setVisible(true);
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
            setBoard(response.data);
            setArticle({id: id, boardCode : response.data.boardCode});
        } catch (error) {
            console.error(error);
        }
    };

    // 이미지 미리보기
    const [images, setImages] = useState([]);
    const [imagePreviews, setImagePreviews] = useState([]);

    const handleImageChange = (e) => {
        const files = Array.from(e.target.files);
        setImages(files);
        const fileReaders = files.map(file => {
            const fileReader = new FileReader();
            fileReader.onload = (e) => {
                setImagePreviews(prev => [...prev, e.target.result]);
            };
            fileReader.readAsDataURL(file);
            return fileReader;
        });
    };

    // 이미지 파일 설정
    const [files, setFiles] = useState([]);

    const handleFileChange = (event) => {
        setFiles(event.target.files);
        handleImageChange();
    };

    // article 설정
    const [article, setArticle] = useState({"id": id});
    const handleArticle = (e) => {
        const{id, value} = e.target;
        setArticle((prevState)=> ({
            ...prevState,
            [id] : value
        }))
    }

    // product 설정
    const [product, setProduct] = useState({"name":"", "price":0, "stock":0});
    const handleProduct = (e) => {
        const{id, value} = e.target;
        setProduct((prevState)=> ({
            ...prevState,
            [id] : value
        }))
    }

    const handleUpload = async () => {
        const formData = new FormData();
        if(path.includes('product')){
            formData.append('product', product)
        }
        formData.append('article', article);
        Array.from(files).forEach(file => {
            formData.append('imgs', file);
        });

        try {
            const response = await axios.post('http://hoopi.p-e.kr/api/hoopi/article', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            alert(response.data);
        } catch (error) {
            console.error(error);
        }
    };




    return(
        <div className='admin-article-container'>
            <div className='admin-product-container' style={{display: visible?'display':'none'}}>
                <div className='admin-product-box'>
                    상품명 : <input type='text' id='name' value={product.name} onChange={handleProduct}/> <br/>
                    가 격 : <input type='text' id='price' value={product.price} onChange={handleProduct}/> <br/>
                    재 고 : <input type='text' id='stock' value={product.stock} onChange={handleProduct}/> <br/>
                </div>
            </div>
            <div className='admin-article-box'>
            <div className='admin-article-title'>
                    <select>
                        <option key={board?.boardCode} id='boardCode' value={board?.boardCode} disabled={true}>
                            {board?.name}
                        </option>
                    </select>
                    <input type='text' id='articleTitle' value={article.articleTitle} onChange={handleArticle}/>
                    <input type='file' onChange={handleFileChange} multiple={true}/>
                </div>
                <div className='admin-article-content'>
                    <textarea id='boardContent' value={article.boardContent} onChange={handleArticle}/>
                    {imagePreviews?.map((image, index) => (
                        <img key={index} src={image} alt={`preview ${index}`}
                             style={{width: '100px', height: '100px'}}/>
                    ))}
                </div>
                <div className='admin-board-footer'>
                    <button onClick={handleUpload}>완료</button>
                    <button>취소</button>
                </div>
            </div>
        </div>
    );
}
export default Board;