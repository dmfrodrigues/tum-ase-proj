import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

import { DeleteOutline } from "@mui/icons-material";
import { useState } from "react";

function DeleteModal({ text }) {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

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
                <Modal.Footer>
                    <Button variant="outline-secondary" size="sm" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="danger" size="sm" onClick={handleClose}>
                        Delete
                    </Button>
                </Modal.Footer>
            </Modal>
        </div >
    );

}

export default DeleteModal;