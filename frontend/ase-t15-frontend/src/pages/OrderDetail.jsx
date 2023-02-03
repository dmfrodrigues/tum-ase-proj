import '../css/page/orderDetail.css'
import { useParams } from "react-router-dom";
import packing_image from '../assets/packing.png'
import delivery_image from '../assets/delivery.png'
import arrived_image from '../assets/arrived.png'
import collected_image from '../assets/collected.png'


function OrderDetail() {
	const { id } = useParams();

	// following should be connected to backend
	const customer_name = "John Smith";
	const order = "Fundamentals of Software Architecture";
	const vendor = "Software Architecture Guide";
	const order_date = "12th January 2023";
	let order_id_is_valid = true;
	let order_history = {
		status: 3,  // 1: packing, 2: delivery, 3: arrived, 4: collected
		pick_up_location: "Garching",
		time_of_status: {
			1: "Today, 8:34 am​​",
			2: "Today, 10:12 am​​",
			3: "Today, 3:15 pm​",
			4: null
		},
	};


	// when window loads:
	// DOM elements are assigened to vars
	// buttons update "order_history.status" -> should be requested from backend
	// after every update, call "updateProgress"
	window.onload = () => {

		const progressBar = document.getElementById("progress-bar");
		const progressNext = document.getElementById("progress-next");
		const progressPrev = document.getElementById("progress-prev");
		const steps = document.querySelectorAll(".step");

		const history_list = document.getElementById("history_list");
		const history_status_1 = document.getElementById("history_status_1");
		const history_status_2 = document.getElementById("history_status_2");
		const history_status_3 = document.getElementById("history_status_3");
		const history_status_4 = document.getElementById("history_status_4");
		let history_status = [history_status_1, history_status_2, history_status_3, history_status_4]

		progressNext.addEventListener("click", () => {
			order_history.status++;
			if (order_history.status > steps.length) {
				order_history.status = steps.length;
			}
			updateProgress();
		});

		progressPrev.addEventListener("click", () => {
			order_history.status--;
			if (order_history.status < 1) {
				order_history.status = 1;
			}
			updateProgress();
		});


		let removeAllChildNodes = parent => {
			while (parent.firstChild) {
				parent.removeChild(parent.firstChild);
			}
		}

		const updateProgress = () => {
			// toggle active class on list items
			steps.forEach((step, i) => {
				if (i < order_history.status) {
					step.classList.add("active");
				} else {
					step.classList.remove("active");
				}
			});
			// set progress bar width  
			progressBar.style.width =
				((order_history.status - 1) / (steps.length - 1)) * 55 + "%";
			// enable disable prev and next buttons
			if (order_history.status === 1) {
				progressPrev.disabled = true;
			} else if (order_history.status === steps.length) {
				progressNext.disabled = true;
			} else {
				progressPrev.disabled = false;
				progressNext.disabled = false;
			}


			// update list
			console.log(order_history.status)
			removeAllChildNodes(history_list);
			for (let i = order_history.status; i > 0; i--) {
				history_list.appendChild(history_status[i - 1]);
			}

		};

		updateProgress();

	};


	return order_id_is_valid ? (
		<div className="orderDetailContainer" scroll="no">

			<div className="orderDetailCaptionContainer">
				<div className="orderDetailCaptionId">Your Order: #{id}</div>
			</div>

			<div className="orderDetailStatusContainer">
				<div className="orderDetailStatusImgStatusContainer">
					<div className="orderDetailStatusImgStatus">
						<img className="orderDetailStatusImg" src={packing_image}></img>
						<div className="orderDetailStatusStatus">Packing</div>
					</div>
					<div className="orderDetailStatusImgStatus">
						<img className="orderDetailStatusImg" src={delivery_image}></img>
						<div className="orderDetailStatusStatus">Delivery</div>
					</div>
					<div className="orderDetailStatusImgStatus">
						<img className="orderDetailStatusImg" src={arrived_image}></img>
						<div className="orderDetailStatusStatus">Arrived</div>
					</div>
					<div className="orderDetailStatusImgStatus">
						<img className="orderDetailStatusImg" src={collected_image}></img>
						<div className="orderDetailStatusStatus">Collected</div>
					</div>
				</div>
				<div id="progress">
					<div id="progress-bar"></div>
					<ul id="progress-num">
						<li className="step">1</li>
						<li className="step">2</li>
						<li className="step">3</li>
						<li className="step">4</li>
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
							<span className="orderDetailContent pb-4 mx-2">Customer</span>
						</div>

						<div>
							<span className="orderDetailTitle">Date:</span>
							<span className="orderDetailContent pb-4 mx-2">12th of January 2023</span>
						</div>
					</div>
				</div>

				<div className="orderContainer">
					<ul className="historyList" id="history_list">
						<li id="history_status_4">
							<div className="historyItemContainer">
								<img className="historyItemImage" src={collected_image}></img>
								<div className="historyItemTextContainer">
									<div className="historyItemTextCaption">Package was collected</div>
									<div className="historyItemTextDescription">The package was collected</div>
								</div>
								<div className="historyItemDateTime">{order_history.time_of_status["4"]}​</div>
							</div>
						</li>
						<li id="history_status_3">
							<div className="historyItemContainer">
								<img className="historyItemImage" src={arrived_image}></img>
								<div className="historyItemTextContainer">
									<div className="historyItemTextCaption">Package was delivered</div>
									<div className="historyItemTextDescription">The package can be collected at {order_history.pick_up_location} pick-up station</div>
								</div>
								<div className="historyItemDateTime">{order_history.time_of_status["3"]}​</div>
							</div>
						</li>
						<li id="history_status_2">
							<div className="historyItemContainer">
								<img className="historyItemImage" src={delivery_image}></img>
								<div className="historyItemTextContainer">
									<div className="historyItemTextCaption">Arriving today</div>
									<div className="historyItemTextDescription">Your Delivery package is arriving today</div>
								</div>
								<div className="historyItemDateTime">{order_history.time_of_status["2"]}</div>
							</div>
						</li>
						<li id="history_status_1">
							<div className="historyItemContainer">
								<img className="historyItemImage" src={packing_image}></img>
								<div className="historyItemTextContainer">
									<div className="historyItemTextCaption">Picked up by Dispatcher​</div>
									<div className="historyItemTextDescription">You package has been picked up by the dispatcher​</div>
								</div>
								<div className="historyItemDateTime">{order_history.time_of_status["1"]}</div>
							</div>
						</li>

					</ul>
				</div>
			</div>


			<div className="debugButtons">
				<button id="progress-prev" className="btn" disabled>Prev</button>
				<button id="progress-next" className="btn">Next</button>
			</div>
		</div>
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