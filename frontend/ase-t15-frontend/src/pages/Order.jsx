import { Link } from "react-router-dom";
import '../css/page/order.css'
import Chart from '../components/Chart';
import { orderData } from "../dummyData";
import { Publish } from '@mui/icons-material';

function Order() {
    return (
        <div className="order">
            <div className="orderTitleContainer">
                <h1 className="orderTitle">order</h1>
                <Link to="/neworder">
                    <button className="orderAddButton">Create</button>
                </Link>
            </div>
            <div className="orderTop">
                <div className="orderTopLeft">
                    <Chart data={orderData} dataKey="Sales" title="Sales Performance" />
                </div>
                <div className="orderTopRight">
                    <div className="orderInfoTop">
                        <img src="https://images.pexels.com/photos/7156886/pexels-photo-7156886.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500" alt="" className="orderInfoImg" />
                        <span className="orderName">Apple Airpods</span>
                    </div>
                    <div className="orderInfoBottom">
                        <div className="orderInfoItem">
                            <span className="orderInfoKey">id:</span>
                            <span className="orderInfoValue">123</span>
                        </div>
                        <div className="orderInfoItem">
                            <span className="orderInfoKey">sales:</span>
                            <span className="orderInfoValue">5123</span>
                        </div>
                        <div className="orderInfoItem">
                            <span className="orderInfoKey">active:</span>
                            <span className="orderInfoValue">yes</span>
                        </div>
                        <div className="orderInfoItem">
                            <span className="orderInfoKey">in stock:</span>
                            <span className="orderInfoValue">no</span>
                        </div>
                    </div>
                </div>
            </div>
            <div className="orderBottom">
                <form className="orderForm">
                    <div className="orderFormLeft">
                        <label>order Name</label>
                        <input type="text" placeholder="Apple AirPod" />
                        <label>In Stock</label>
                        <select name="inStock" id="idStock">
                            <option value="yes">Yes</option>
                            <option value="no">No</option>
                        </select>
                        <label>Active</label>
                        <select name="active" id="active">
                            <option value="yes">Yes</option>
                            <option value="no">No</option>
                        </select>
                    </div>
                    <div className="orderFormRight">
                        <div className="orderUpload">
                            <img src="https://images.pexels.com/photos/7156886/pexels-photo-7156886.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500" alt="" className="orderUploadImg" />
                            <label for="file">
                                <Publish />
                            </label>
                            <input type="file" id="file" style={{ display: "none" }} />
                        </div>
                        <button className="orderButton">Update</button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Order;