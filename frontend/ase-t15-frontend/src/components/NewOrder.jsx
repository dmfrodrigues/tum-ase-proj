/* Creates a form to edit an order */
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import { useState } from "react";
import { AddCircle, AddCircleOutline } from '@mui/icons-material';

function NewOrder({ customers, dispatchers, boxes }) {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <div className="orderListNew">
            <Button
                variant="outline-success"
                startIcon={<AddCircle />}
                onClick={handleShow}
                className="orderListNewButton"
            >
                <AddCircleOutline />
                <span className="orderListNewButtonSpan">New Order</span>
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>New Order</Modal.Title>
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
                                type="radio"
                                id="radio-1"
                                value="pending"
                                name="formBasicStatus"
                                label={`Pending`}
                            />
                            <Form.Check
                                type="radio"
                                id="radio-2"
                                value="canceled"
                                name="formBasicStatus"
                                label={`Canceled`}
                            />
                            <Form.Check
                                type="radio"
                                id="radio-3"
                                value="delivered"
                                name="formBasicStatus"
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
                        Create
                    </Button>
                </Modal.Footer>
            </Modal>
        </div >
    );

}

export default NewOrder;