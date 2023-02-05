import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

import { DeleteOutline } from "@mui/icons-material";
import { useState } from "react";

function DeleteModal({ text, handleDelete }) {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const handleSubmit = () => {
        handleDelete();
        handleClose();
    }

    return (
        <div>

            <DeleteOutline
                className="orderListDelete"
                onClick={handleShow}
            />

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{text}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Are you sure that you want to delete?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="outline-secondary" size="sm" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="danger" size="sm" onClick={handleSubmit}>
                        Delete
                    </Button>
                </Modal.Footer>
            </Modal>
        </div >
    );

}

export default DeleteModal;