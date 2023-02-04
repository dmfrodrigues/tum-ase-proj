/* Creates a form to edit an order */
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import { useState } from "react";

function moveToFirst(arr, id) {
    arr = [...arr];
    const index = arr.findIndex((el) => el.id === id);
    const first = arr[index];
    arr.sort(function (x, y) { return x == first ? -1 : y == first ? 1 : x < y; });
    return arr;
}

function EditBox({ box, customers }) {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    customers = moveToFirst(customers, box.customer.id);

    return (
        <div>
            <Button variant="outline-success" size="sm" className="boxListEditBtn" onClick={handleShow}>
                Edit
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Edit Box</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3" controlId="formBasicName">
                            <Form.Label>Insert Name</Form.Label>
                            <Form.Control type="text" value={box.username} />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicAddress">
                            <Form.Label>Insert Address</Form.Label>
                            <Form.Control type="text" value={box.address} />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicCustomer">
                            <Form.Label>Select Customer</Form.Label>
                            <Form.Select aria-label="Customer select" size="sm">
                                {
                                    customers.map((customer) => {
                                        return <option key={customer.id} value={customer.id}>{customer.name}</option>
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
                    <Button variant="success" size="sm" onClick={handleClose}>
                        Create
                    </Button>
                </Modal.Footer>
            </Modal>
        </div >
    );

}

export default EditBox;