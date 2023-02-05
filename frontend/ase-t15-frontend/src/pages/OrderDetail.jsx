import '../css/page/orderDetail.css'
import { useParams } from "react-router-dom";
import packing_image from '../assets/packing.png'
import delivery_image from '../assets/delivery.png'
import arrived_image from '../assets/arrived.png'
import collected_image from '../assets/collected.png'
import { useDispatch, useSelector } from "react-redux";
import { getOrder } from "../actions/orders";
import { useEffect } from 'react';
import { useState } from 'react';
import { width } from '@mui/system';


function OrderDetail() {
	const { id } = useParams();

	const dispatch = useDispatch()
	const order = useSelector(state => state.orders.order)
	const [customer_name, setCustomerName] = useState("");
	const [box_name, setBoxName] = useState("");
	const [pickupAddress, setPickupAddress] = useState("");
	const [order_date, setOrderDate] = useState("");
	const [order_history, setOrderHistory] = useState({
		status: 1,
		time_of_status: [],
	});
	const [order_id_is_valid, setOrderIdIsValid] = useState(false);

	useEffect(() => {
		dispatch(getOrder(id))
	}, [])

	useEffect(() => {
		if (order.customer) {
			setCustomerName(order.customer.name);
			setBoxName(order.box.username);
			setPickupAddress(order.pickupAddress);
			setOrderDate(order.events.length > 0 ? new Date(order.events[0].date).toDateString() : null);
			let state_to_status = {
				"ORDERED": 1,
				"PICKED_UP": 2,
				"DELIVERED": 3,
				"COMPLETE": 4,
			}
			setOrderHistory({
				status: state_to_status[order.events.length > 0 ? order.events[order.events.length - 1].state : "ORDERED"],
				time_of_status: order.events.length > 0 ?
					order.events.map(event => new Date(event.date).toDateString())
					: [],
			});
			console.log(order_history)
			setOrderIdIsValid(true);
		}
	}, [order])


	return order_id_is_valid ? (
		<div className="orderDetailContainer" scroll="no">

			<div className="orderDetailCaptionContainer">
				<div className="orderDetailCaptionId">Your Order: #{id}</div>
			</div>

			<div className="orderDetailStatusContainer">
				<div className="orderDetailStatusImgStatusContainer">
					<div className="orderDetailStatusImgStatus">
						<img className="orderDetailStatusImg" src={packing_image}></img>
						<div className="orderDetailStatusStatus">Ordered</div>
					</div>
					<div className="orderDetailStatusImgStatus">
						<img className="orderDetailStatusImg" src={delivery_image}></img>
						<div className="orderDetailStatusStatus">Picked up</div>
					</div>
					<div className="orderDetailStatusImgStatus">
						<img className="orderDetailStatusImg" src={arrived_image}></img>
						<div className="orderDetailStatusStatus">Delivered</div>
					</div>
					<div className="orderDetailStatusImgStatus">
						<img className="orderDetailStatusImg" src={collected_image}></img>
						<div className="orderDetailStatusStatus">Completed</div>
					</div>
				</div>
				<div id="progress">
					<div id="progress-bar" style={{ width: ((order_history.status - 1) / (4 - 1)) * 55 + "%" }}></div>

					<ul id="progress-num">
						<li className={`step ${order_history.status >= 1 ? "active" : ""}`}>1</li>
						<li className={`step ${order_history.status >= 2 ? "active" : ""}`}>2</li>
						<li className={`step ${order_history.status >= 3 ? "active" : ""}`}>3</li>
						<li className={`step ${order_history.status >= 4 ? "active" : ""}`}>4</li>
					</ul>
				</div>
			</div>

			<div className="bottomContainer">

				<div className="orderContainer">
					<div className="orderDetailsTitle">
						Order Details
					</div>
					<div className="orderDetail p-4">
						<div>
							<span className="orderDetailTitle">Customer:</span>
							<span className="orderDetailContent pb-4 mx-2"> {customer_name} </span>
						</div>

						<div>
							<span className="orderDetailTitle">Box Name:</span>
							<span className="orderDetailContent pb-4 mx-2"> {box_name} </span>
						</div>

						<div>
							<span className="orderDetailTitle">Pickup Address:</span>
							<span className="orderDetailContent pb-4 mx-2"> {pickupAddress} </span>
						</div>

						<div>
							<span className="orderDetailTitle">Date:</span>
							<span className="orderDetailContent pb-4 mx-2"> {order_date} </span>
						</div>
					</div>
				</div>

				<div className="orderContainer">
					<ul className="historyList" id="history_list">
						{order_history.status >= 4 &&
							<li id="history_status_4">
								<div className="historyItemContainer">
									<img className="historyItemImage" src={collected_image}></img>
									<div className="historyItemTextContainer">
										<div className="historyItemTextCaption">Delivery Complete</div>
										<div className="historyItemTextDescription">The delivery was marked as complete</div>
									</div>
									<div className="historyItemDateTime">{order_history.time_of_status[3]}​</div>
								</div>
							</li>
						}
						{order_history.status >= 3 &&
							<li id="history_status_3">
								<div className="historyItemContainer">
									<img className="historyItemImage" src={arrived_image}></img>
									<div className="historyItemTextContainer">
										<div className="historyItemTextCaption">Package was delivered</div>
										<div className="historyItemTextDescription">The package can be collected at {pickupAddress}</div>
									</div>
									<div className="historyItemDateTime">{order_history.time_of_status[2]}​</div>
								</div>
							</li>
						}
						{order_history.status >= 2 &&
							<li id="history_status_2">
								<div className="historyItemContainer">
									<img className="historyItemImage" src={delivery_image}></img>
									<div className="historyItemTextContainer">
										<div className="historyItemTextCaption">Picked up</div>
										<div className="historyItemTextDescription">Your delivery package was picked up and is on the way</div>
									</div>
									<div className="historyItemDateTime">{order_history.time_of_status[1]}</div>
								</div>
							</li>
						}
						<li id="history_status_1">
							<div className="historyItemContainer">
								<img className="historyItemImage" src={packing_image}></img>
								<div className="historyItemTextContainer">
									<div className="historyItemTextCaption">Package is ordered</div>
									<div className="historyItemTextDescription">Your package has been ordered successfully</div>
								</div>
								<div className="historyItemDateTime">{order_history.time_of_status[0]}</div>
							</div>
						</li>

					</ul>
				</div>
			</div>
		</div >
	) : (
		<div className="orderDetailContainer" scroll="no" style={{
			fontWeight: "bolder",
			fontSize: "3em",
			textAlign: "center"
		}}>
			Order #{id} not found
		</div>
	);
}

export default OrderDetail;



/*


*/