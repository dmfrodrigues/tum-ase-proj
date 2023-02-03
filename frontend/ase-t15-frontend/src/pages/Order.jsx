import { Link } from "react-router-dom";
import '../css/page/order.css'
import Chart from '../components/Chart';
import { orderData } from "../dummyData";
import { Inventory2Outlined, LocalShippingOutlined, MarkEmailReadOutlined, Publish, PublishedWithChanges, RadioButtonUnchecked, TaskAlt } from '@mui/icons-material';

function Order() {
    return (
        <div className="order">
            <div className="orderTop">
                <div className="orderTopRight">
                    <div className="orderInfoTop">
                        <div className="orderInfoPacking">
                            <Inventory2Outlined className="orderIcon" />
                            Packing
                        </div>

                        <TaskAlt className="orderIconGreen" />
                    </div>
                    <div className="orderInfoTop">
                        <div className="orderInfoPacking">
                            <LocalShippingOutlined className="orderIcon" fontSize="larger" />
                            Delivery
                        </div>
                        <TaskAlt className="orderIconGreen" />
                    </div>
                    <div className="orderInfoTop">
                        <div className="orderInfoPacking">
                            <MarkEmailReadOutlined className="orderIcon" />
                            Arrived
                        </div>
                        <TaskAlt className="orderIconGreen" />
                    </div>
                    <div className="orderInfoTop">
                        <div className="orderInfoPacking">
                            <PublishedWithChanges className="orderIcon" />
                            Collected
                        </div>
                        <RadioButtonUnchecked className="orderIcon" />
                    </div>
                </div>
            </div>
            <div className="orderBottom">
                <div className="orderDetailsTitle">
                    Order Details
                </div>
                <div className="orderDetail p-4">
                    <div>
                        <span className="orderDetailTitle">Customer:</span>
                        <span className="orderDetailContent pb-4 mx-2">Customer</span>
                    </div>

                    <div>
                        <span className="orderDetailTitle">Date:</span>
                        <span className="orderDetailContent pb-4 mx-2">12th of January 2023</span>
                    </div>
                </div>
            </div>
        </div >
    );
}

export default Order;