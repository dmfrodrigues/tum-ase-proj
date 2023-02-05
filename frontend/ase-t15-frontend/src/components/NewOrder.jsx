/* Creates a form to edit an order */
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import { useState } from "react";
import { useSelector } from 'react-redux';
import { AddCircleOutline } from '@mui/icons-material';
import { useDispatch } from 'react-redux'
import { createOrder } from '../actions/orders';

function NewOrder({ customers, deliverers, boxes }) {
    const dispatch = useDispatch();
    const user = useSelector((state) => state.auth.user);
    const [show, setShow] = useState(false);
    const [customerId, setCustomerId] = useState(customers?.[0]?.id);
    const [delivererId, setDelivererId] = useState(deliverers?.[0]?.id);
    const [boxId, setBoxId] = useState(boxes?.[0]?.id);
    const [address, setAddress] = useState("");

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const handleSubmit = () => {
        // console.log("Submitting new order");
        // console.log("Customer: " + customerId);
        // console.log("Deliverer: " + delivererId);
        // console.log("Dispatcher: " + user.id);
        // console.log("Box: " + boxId);
        // console.log("Address: " + address);
        dispatch(createOrder(customerId, user.id, delivererId, address, boxId));
        handleClose();
        window.location.reload();
    }

    return (
        <div className="orderListNew">
            <Button
                variant="outline-success"
                onClick={handleShow}
                className="orderListNewButton"
            >
                <AddCircleOutline />
                <span className="orderListNewButtonSpan">Order</span>
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>New Order</Modal.Title>
                    <p>Customer: {user?.id}</p>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3" controlId="formBasicCustomer">
                            <Form.Label>Select Customer</Form.Label>
                            <Form.Select aria-label="Customer select" size="sm" onChange={(e) => setCustomerId(e.target.value)}>
                                {
                                    customers.map((customer) => {
                                        return <option key={customer.id} value={customer.id}>{customer.name}</option>
                                    })
                                }
                            </Form.Select>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicDeliverer">
                            <Form.Label>Select Deliverer</Form.Label>
                            <Form.Select aria-label="Deliverer select" size="sm" onChange={(e) => setDelivererId(e.target.value)}>
                                {
                                    deliverers.map((deliverer) => {
                                        return <option key={deliverer.id} value={deliverer.id}>{deliverer.name}</option>
                                    })
                                }
                            </Form.Select>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicAddress" onChange={(e) => setAddress(e.target.value)}>
                            <Form.Label>Address</Form.Label>
                            <Form.Control type="text" placeholder="Enter address" />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicBox">
                            <Form.Label>Select Pick-up Box</Form.Label>
                            <Form.Select aria-label="Pick-up Box select" size="sm" onChange={(e) => setBoxId(e.target.value)}>
                                {
                                    boxes.map((box) => {
                                        return <option key={box.id} value={box.id}>{box.username}</option>
                                    })
                                }
                            </Form.Select>
                        </Form.Group>
                    </Form>

                </Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" size="sm" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="success" size="sm" onClick={handleSubmit}>
                        Create
                    </Button>
                </Modal.Footer>
            </Modal>
        </div >
    );

}

export default NewOrder;