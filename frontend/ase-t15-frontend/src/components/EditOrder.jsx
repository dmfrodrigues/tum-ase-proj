/* Creates a form to edit an order */
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import { useState } from "react";
import { OrderStatus } from '../pages/Order';

function moveToFirst(arr, id) {
    arr = [...arr];
    const index = arr.findIndex((el) => el.id === id);
    const first = arr[index];
    arr.sort(function (x, y) { return x == first ? -1 : y == first ? 1 : x < y; });
    return arr;
}

function EditOrder({ customers, dispatchers, boxes, order }) {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    boxes = moveToFirst(boxes, order.boxId);
    customers = moveToFirst(customers, order.customerId);
    dispatchers = moveToFirst(dispatchers, order.dispatcherId);

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
                            <Form.Select aria-label="Customer select" size="sm">
                                {
                                    customers.map((customer) => {
                                        return <option key={customer.id} value={customer.id}>{customer.name}</option>
                                    })
                                }
                            </Form.Select>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId={`dispatcherId${order.id}`}>
                            <Form.Label>Select Deliverer</Form.Label>
                            <Form.Select aria-label="Deliverer select" size="sm">
                                {
                                    dispatchers.map((dispatcher) => {
                                        return <option key={dispatcher.id} value={dispatcher.id}>{dispatcher.name}</option>
                                    })
                                }
                            </Form.Select>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId={`boxId${order.id}`}>
                            <Form.Label>Select Pick-up Box</Form.Label>
                            <Form.Select aria-label="Pick-up Box select" size="sm">
                                {
                                    boxes.map((box) => {
                                        return <option key={box.id} value={box.id}>{box.username}</option>
                                    })
                                }
                            </Form.Select>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId={`status${order.id}`}>
                            <Form.Label>Select Status</Form.Label>
                            {
                                Object.keys(OrderStatus).map((key) => {
                                    let status = OrderStatus[key];
                                    let orderStatus = order.events[order.events.length - 1].state;
                                    return <Form.Check
                                        defaultChecked={orderStatus === key ? "checked" : ""}
                                        key={key}
                                        type="radio"
                                        id={`radio-${key}${order.id}`}
                                        value={status}
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
                    <Button variant="success" size="sm" onClick={handleClose}>
                        Save Changes
                    </Button>
                </Modal.Footer>
            </Modal>
        </div >
    );

}

export default EditOrder;