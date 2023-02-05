import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

import { DeleteOutline } from "@mui/icons-material";
import { useState, useEffect } from "react";
import { useSelector, useDispatch } from 'react-redux';
import { editOrderState } from '../actions/orders';

function ConfirmOrderState() {
    const dispatch = useDispatch();
    const [show, setShow] = useState(false);
    const order = useSelector(state => state.orders.order)
    const [orderState, setOrderState] = useState(order.events ? order.events[order.events.length - 1].state : "ORDERED");

    useEffect(() => {
        if (order.events) {
            setOrderState(order.events[order.events.length - 1].state);
        }
    }, [order])

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const handleSubmit = () => {
        const futureState = orderState === "ORDERED" ? "PICKED_UP" : orderState === "PICKED_UP" ? "DELIVERED" : "";
        // console.log("Order completed");
        // console.log(futureState);
        dispatch(editOrderState(order.id, futureState));
        handleClose();
        window.location.reload();
    }

    return (
        (orderState === "ORDERED" || orderState === "PICKED_UP") &&
        <div>
            <Button variant="outline-success" size="sm" onClick={handleShow}>
                Mark order as  {orderState === "ORDERED" ? "picked up" : orderState === "PICKED_UP" ? "delivered" : ""}
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Mark order</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Are you sure that you want to mark order?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="outline-secondary" size="sm" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="success" size="sm" onClick={handleSubmit}>
                        Confirm
                    </Button>
                </Modal.Footer>
            </Modal>
        </div >
    );

}

export default ConfirmOrderState;