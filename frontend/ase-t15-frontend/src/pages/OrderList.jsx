import '../css/page/orderList.css'
import { DataGrid } from '@mui/x-data-grid';
import { orderRows } from "../dummyData";
import { dispatcherRows, customerRows, boxRows } from "../dummyData";
import { useState } from "react";
import OrderIcon from '../components/OrderIcon';
import EditOrder from '../components/EditOrder';
import DeleteModal from '../components/DeleteModal';
import NewOrder from '../components/NewOrder';

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
      renderHeader: () => {
        return (
          <NewOrder customers={customerRows} dispatchers={dispatcherRows} boxes={boxRows} />
        );
      },
      flex: 1,
      sortable: false,
      filterable: false,
      renderCell: (params) => {
        return (
          <div className="orderListEdit">
            <EditOrder customers={customerRows} dispatchers={dispatcherRows} boxes={boxRows} order={params.row} />
            <DeleteModal />
          </div>
        );
      },
    },
  ];

  return (
    <div className="orderList">
      <DataGrid
        className='orderListTable'
        rows={data}
        disableSelectionOnClick
        columns={columns}
        pageSize={8}
      />
    </div>
  );
}

export default OrderList;