import './Main.css';
import Series from "./Series";
import {useEffect, useState} from "react";
import axios from "axios";
const Main = () => {

    useEffect(() => {
        productNew();
    }, []);

    const[newProduct, setNewProduct] = useState([]);
    const productNew = async () => {
        try{
            const response = await axios.get('http://hoopi.p-e.kr/api/hoopi/product-new');
            setNewProduct(response.data);
        } catch (e){
            console.log(e);
        }
    }

    return (
        <div className="main-container">
            <Series/>
            <div className= "main-letter-container">신상품</div>
            <div className="main-new-product-container">
                {newProduct?.map(np=> (
                    <div className="main-new-product-box" key={np.product.productCode}>
                        <table>
                            <thead>
                            <tr>
                                <td colSpan={2}>
                                    <div>
                                        <img src={`https://${process.env.S3_NM}.s3.ap-northeast-2.amazonaws.com${np.productImg.imgKey}`} alt='맛나요'/>
                                    </div>
                                </td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th colSpan={2}>{np.product.name}</th>
                            </tr>
                            <tr>
                                <td>{np.product.price}</td>
                                <td>{np.boardContent}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                ))}
            </div>
        </div>

    );
}

export default Main;