import '../css/component/orderIcon.css'

import { Cancel, CancelOutlined, CheckCircle, CheckCircleOutline, LocalShippingOutlined, Timer, Timer10Outlined, Timer3Outlined, TimerOutlined } from "@mui/icons-material";

function OrderIcon({ status }) {

    let st = "orderIcon" + status[0].toUpperCase() + status.slice(1);
    return (
        < div className={st} >
            <LocalShippingOutlined fontSize="larger" className="shippingIcon" />
            {status === "pending" && <TimerOutlined fontSize="small" className='statusIcon' />}
            {status === "delivered" && <CheckCircleOutline fontSize="small" className='statusIcon' />}
            {status === "canceled" && <CancelOutlined fontSize="small" className='statusIcon' />}
        </div >
    );
}

export default OrderIcon;