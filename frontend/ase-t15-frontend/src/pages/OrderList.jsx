import '../css/page/orderList.css'
import { DataGrid } from '@mui/x-data-grid';
import { Cancel, CheckCircle, DeleteOutline, LocalShipping, LocalShippingOutlined, Timer } from "@mui/icons-material";
import { orderRows } from "../dummyData";
import { Link } from "react-router-dom";
import { dispatcherRows, customerRows, boxRows } from "../dummyData";
import { useState } from "react";
import OrderIcon from '../components/OrderIcon';
import EditOrder from '../components/EditOrder';

function OrderList() {
  const [data, setData] = useState(orderRows);

  const handleDelete = (id) => {
    setData(data.filter((item) => item.id !== id));
  };

  const columns = [
    { field: "id", headerName: "ID" },
    {
      field: "order",
      headerName: "Order",
      flex: 1,
      width: 150,
      renderCell: (params) => {
        return (
          <div className="orderListItem">
            <OrderIcon status={params.row.status} />
          </div>
        );
      },
    },
    {
      field: "customer",
      headerName: "Customer",
      flex: 1,
    },
    {
      field: "dispatcher",
      headerName: "Dispatcher",
      flex: 1,
    },
    {
      field: "status",
      headerName: "Status",
      width: 150,
      flex: 1,
      renderCell: (params) => {
        var status = "orderStatus" + params.row.status[0].toUpperCase() + params.row.status.slice(1);
        return (
          <div className={status}>
            {params.row.status[0].toUpperCase() + params.row.status.slice(1)}
          </div >
        );
      }
    },
    {
      field: "action",
      headerName: "Action",
      flex: 1,
      renderCell: (params) => {
        return (
          <>
            <EditOrder customers={customerRows} dispatchers={dispatcherRows} boxes={boxRows} order={params.row} />
            <DeleteOutline
              className="orderListDelete"
              onClick={() => handleDelete(params.row.id)}
            />
          </>
        );
      },
    },
  ];

  return (
    <div className="orderList">
      <DataGrid
        rows={data}
        disableSelectionOnClick
        columns={columns}
        pageSize={8}
        checkboxSelection
      />
    </div>
  );
}

export default OrderList;