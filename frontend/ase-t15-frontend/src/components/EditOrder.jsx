/* Creates a form to edit an order */
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import { useState } from "react";

function moveToFirst(arr, id) {
    const index = arr.findIndex((el) => el.id === id);
    const first = arr[index];
    arr.sort(function (x, y) { return x == first ? -1 : y == first ? 1 : x < y; });
}

function EditOrder({ customers, dispatchers, boxes, order }) {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    moveToFirst(boxes, order.boxId);
    moveToFirst(customers, order.customerId);
    moveToFirst(dispatchers, order.dispatcherId);

    return (
        <div>
            <Button variant="outline-success" size="sm" className="orderListEdit" onClick={handleShow}>
                Edit
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Edit Order</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3" controlId="formBasicCustomer">
                            <Form.Label>Select Customer</Form.Label>
                            <Form.Select aria-label="Customer select" size="sm">
                                {
                                    customers.map((customer) => {
                                        return <option value={customer.id}>{customer.username}</option>
                                    })
                                }
                            </Form.Select>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicDeliverer">
                            <Form.Label>Select Deliverer</Form.Label>
                            <Form.Select aria-label="Deliverer select" size="sm">
                                {
                                    dispatchers.map((dispatcher) => {
                                        return <option value={dispatcher.id}>{dispatcher.username}</option>
                                    })
                                }
                            </Form.Select>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicBox">
                            <Form.Label>Select Pick-up Box</Form.Label>
                            <Form.Select aria-label="Pick-up Box select" size="sm">
                                {
                                    boxes.map((box) => {
                                        return <option value={box.id}>{box.name}</option>
                                    })
                                }
                            </Form.Select>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicStatus">
                            <Form.Label>Select Status</Form.Label>
                            <Form.Check
                                checked={order.status === "pending" ? "checked" : ""}
                                type="radio"
                                id={`default-radio`}
                                label={`Pending`}
                            />
                            <Form.Check
                                checked={order.status === "canceled" ? "checked" : ""}
                                type="radio"
                                id={`default-radio`}
                                label={`Canceled`}
                            />
                            <Form.Check
                                checked={order.status === "delivered" ? "checked" : ""}
                                type="radio"
                                id={`default-radio`}
                                label={`Delivered`}
                            />

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