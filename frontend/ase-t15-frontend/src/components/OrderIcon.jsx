import '../css/component/orderIcon.css'

import { CheckCircleOutline, LocalPostOfficeOutlined, LocalShippingOutlined, Timer, Timer10Outlined, Timer3Outlined, TimerOutlined } from "@mui/icons-material";

function OrderIcon({ status }) {

    let st = "orderIcon" + status;
    return (
        < div className={st} >
            <LocalShippingOutlined fontSize="larger" className="shippingIcon" />
            {status === "ORDERED" && <TimerOutlined fontSize="small" className='statusIcon' />}
            {status === "PICKED_UP" && <LocalPostOfficeOutlined fontSize="small" className='statusIcon' />}
            {status === "DELIVERED" && <CheckCircleOutline fontSize="small" className='statusIcon' />}
            {status === "COMPLETED" && <CheckCircleOutline fontSize="small" className='statusIcon' />}
        </div >
    );
}

export default OrderIcon;