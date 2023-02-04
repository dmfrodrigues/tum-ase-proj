import '../css/page/orderList.css'
import { DataGrid } from '@mui/x-data-grid';
import { orderRows } from "../dummyData";
import { useEffect, useState } from "react";
import OrderIcon from '../components/OrderIcon';
import EditOrder from '../components/EditOrder';
import DeleteModal from '../components/DeleteModal';
import NewOrder from '../components/NewOrder';
import { Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux'
import { OrderStatus } from './Order';
import { getOrders } from '../actions/orders';
import { getCustomers, getDeliverers } from '../actions/users';
import { getBoxes } from '../actions/boxes';

function OrderList() {
  const dispatch = useDispatch()

  const [data, setData] = useState(orderRows);
  const [orders, setOrders] = useSelector(state => state.orders)
  const [customers, setCustomers] = useSelector(state => state.users.customers);
  const [deliverers, setDeliverers] = useSelector(state => state.users.deliverers);
  const [boxes, setBoxes] = useSelector(state => state.boxes);

  useEffect(() => {
    dispatch(getOrders())
    dispatch(getCustomers())
    dispatch(getDeliverers())
    dispatch(getBoxes())
  }, [])

  const handleDelete = (id) => {
    setData(data.filter((item) => item.id !== id));
    // TODO
  };

  const getStatus = (order) => {
    return order.events[order.events.length - 1].state;
  }

  const columns = [
    {
      field: "id",
      headerName: "ID",
      width: 100,
      flex: 1,
    },
    {
      field: "order",
      headerName: "Order",
      width: 150,
      flex: 1,
      renderCell: (params) => {
        return (
          <Link to={"/orders/" + params.row.id}>
            <div className="orderListItem">
              <OrderIcon status={getStatus(params.row)} />
            </div>
          </Link >
        );
      },
    },
    {
      field: "customer",
      valueGetter: (params) => {
        return params.row.customer.name;
      },
      headerName: "Customer",
      flex: 1,
    },
    {
      field: "Dispatcher",
      valueGetter: (params) => {
        return params.row.createdBy.name;
      },
      headerName: "Dispatcher",
      flex: 1,
    },
    {
      field: "status",
      headerName: "Status",
      width: 150,
      flex: 1,
      renderCell: (params) => {
        var status = getStatus(params.row);
        var statusClass = "orderList" + status;
        return (
          <div className={statusClass}>
            {OrderStatus[status]}
          </div >
        );
      }
    },
    {
      renderHeader: () => {
        return (
          <NewOrder customers={customers} dispatchers={deliverers} boxes={boxes} />
        );
      },
      flex: 1,
      field: "action",
      sortable: false,
      filterable: false,
      renderCell: (params) => {
        return (
          <div className="orderListEdit">
            <EditOrder customers={customers} dispatchers={deliverers} boxes={boxes} order={params.row} />
            <DeleteModal text="Confirm Order Deletion" />
          </div>
        );
      },
    },
  ];

  return (
    <div className="orderList">
      <DataGrid
        className='orderListTable'
        rows={orders}
        disableSelectionOnClick
        columns={columns}
        pageSize={8}
      />
    </div>
  );
}

export default OrderList;