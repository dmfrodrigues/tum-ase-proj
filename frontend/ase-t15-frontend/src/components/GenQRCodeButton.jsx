import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

import { useState, useEffect, useRef } from "react";
import { useSelector, useDispatch } from 'react-redux';
import QRCode from "react-qr-code";


function GenQRCodeButton() {
    const dispatch = useDispatch();
    const [show, setShow] = useState(false);
    const order = useSelector(state => state.orders.order)
    const [orderId, setOrderId] = useState(order?.id);

    useEffect(() => {
        if (order.events) {
            setOrderId(order.id);
        }
    }, [order])

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const handleSubmit = () => {
        console.log(orderId);
        handleClose();
    }

    const canvasRef = useRef(null)

    return (
        <div>
            <Button variant="outline-success" size="sm" className='mt-3' onClick={handleShow}>
                Show QR Code
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>QR Code</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="d-flex justify-content-center">
                        <QRCode value={`http://138.246.237.201/orders/${orderId}`}
                            size="250" />
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="outline-secondary" size="sm" onClick={handleSubmit}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        </div >
    );

}

export default GenQRCodeButton;