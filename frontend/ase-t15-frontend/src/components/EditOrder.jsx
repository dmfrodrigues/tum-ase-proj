/* Creates a form to edit an order */
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import { useState } from "react";
import { useDispatch } from 'react-redux'
import { OrderStatus } from '../pages/Order';
import { editOrder } from '../actions/orders';

function moveToFirst(arr, id) {
    arr = [...arr];
    const index = arr.findIndex((el) => el.id === id);
    const first = arr[index];
    arr.sort(function (x, y) { return x == first ? -1 : y == first ? 1 : x < y; });
    return arr;
}

function EditOrder({ customers, deliverers, boxes, order }) {
    const dispatch = useDispatch();
    const [show, setShow] = useState(false);
    const [customerId, setCustomerId] = useState(order.customer?.id);
    const [delivererId, setDelivererId] = useState(order.deliverer?.id);
    const [boxId, setBoxId] = useState(order.box?.id);
    const [pickupAddress, setAddress] = useState(order.pickupAddress);
    const [state, setStatus] = useState(
        order.history ? order.history[order.history.length - 1].status : OrderStatus.ORDERED
    );

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const handleSubmit = () => {
        // console.log("Submitting new order");
        // console.log("Customer: " + customerId);
        // console.log("Deliverer: " + delivererId);
        // console.log("Box: " + boxId);
        // console.log("Address: " + address);
        // console.log("Status: " + status);
        dispatch(editOrder(order.id, { customerId, delivererId, boxId, pickupAddress, state }));
        handleClose();
    }

    boxes = moveToFirst(boxes, boxId);
    customers = moveToFirst(customers, customerId);
    deliverers = moveToFirst(deliverers, delivererId);

    return (
        <div>
            <Button variant="outline-success" size="sm" className="orderListEditBtn" onClick={handleShow}>
                Edit
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Edit Order</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3" controlId={`customerId${order.id}`}>
                            <Form.Label>Select Customer</Form.Label>
                            <Form.Select aria-label="Customer select" size="sm" onChange={(e) => setCustomerId(e.target.value)}>
                                {
                                    customers.map((customer) => {
                                        return <option key={customer.id} value={customer.id}>{customer.name}</option>
                                    })
                                }
                            </Form.Select>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId={`dispatcherId${order.id}`}>
                            <Form.Label>Select Deliverer</Form.Label>
                            <Form.Select aria-label="Deliverer select" size="sm" onChange={(e) => setDelivererId(e.target.value)}>
                                {
                                    deliverers.map((dispatcher) => {
                                        return <option key={dispatcher.id} value={dispatcher.id}>{dispatcher.name}</option>
                                    })
                                }
                            </Form.Select>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicAddress">
                            <Form.Label>Address</Form.Label>
                            <Form.Control type="text" value={pickupAddress} onChange={(e) => setAddress(e.target.value)} />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId={`boxId${order.id}`}>
                            <Form.Label>Select Pick-up Box</Form.Label>
                            <Form.Select aria-label="Pick-up Box select" size="sm" onChange={(e) => setBoxId(e.target.value)}>
                                {
                                    boxes.map((box) => {
                                        return <option key={box.id} value={box.id}>{box.username}</option>
                                    })
                                }
                            </Form.Select>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId={`status${order.id}`} onChange={(e) => setStatus(e.target.value)}>
                            <Form.Label>Select Status</Form.Label>
                            {
                                Object.keys(OrderStatus).map((key) => {
                                    let status = OrderStatus[key];
                                    let orderStatus;
                                    if (order.events.length === 0)
                                        orderStatus = OrderStatus.ORDERED;
                                    else
                                        orderStatus = order.events[order.events.length - 1].state;
                                    return <Form.Check
                                        defaultChecked={orderStatus === key ? "checked" : ""}
                                        key={key}
                                        type="radio"
                                        id={`radio-${key}${order.id}`}
                                        value={key}
                                        name={`formBasicStatus${order.id}`}
                                        label={status}
                                    />
                                })
                            }
                        </Form.Group>
                    </Form>

                </Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" size="sm" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="success" size="sm" onClick={handleSubmit}>
                        Save Changes
                    </Button>
                </Modal.Footer>
            </Modal>
        </div >
    );

}

export default EditOrder;