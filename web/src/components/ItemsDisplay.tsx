import { useEffect, useState } from "react";
import type { Product } from "../types/Product";
import { useAuth } from "../util/AuthProvider";
import type { Toast } from "../types/Toast";
import { useApi } from "../hooks/useApi";
import style from "../style/RestaurantProfile.module.css";

const ItemsDisplay = () => {
  const [openCategory, setOpenCategory] = useState<string | null>(null);
  const [isAdding, setIsAdding] = useState(false);
  const { token } = useAuth();
  const [name, setName] = useState("");
  const [price, setPrice] = useState<number | "">("");
  const [editingProductId, setEditingProductId] = useState<number | null>(null);
  const [editName, setEditName] = useState("");
  const [editPrice, setEditPrice] = useState<number | "">("");
  const [createdItemToast, setCreatedItemToast] = useState<Toast | null>(null);
  const [removedItemToast, setRemovedItemToast] = useState<Toast | null>(null);
  const [editedItemToast, setEditedItemToast] = useState<Toast | null>(null);
  const createItemApi = useApi<Product>(
    `${import.meta.env.VITE_API_URL}restaurant/protected/create-item`,
    token
  );
  const deleteItemApi = useApi<Product>(
    `${import.meta.env.VITE_API_URL}restaurant/protected/delete-item/`,
    token
  );
  const updateItemApi = useApi<Product>(
    `${import.meta.env.VITE_API_URL}restaurant/protected/update-item`,
    token
  );
  const [items, setItems] = useState<Record<string, Product[]>>({});
    const itemsApi = useApi<Record<string, Product[]>>(
      `${import.meta.env.VITE_API_URL}restaurant/protected/my-items-grouped`,
      token
    );

  const saveMethod = async () => {
    if (!token || !openCategory || !name || price == null) return;

    const body = {
      name: name,
      price: price,
      category: openCategory,
    };

    const fetchCreatedProduct = async () => {
      const res = await createItemApi.post("", body);
      if (res) {
        const newProduct = res.data;

        setItems((prev) => ({
          ...prev,
          [openCategory]: prev[openCategory]
            ? [...prev[openCategory], newProduct]
            : [newProduct],
        }));

        setCreatedItemToast({ message: res.message, success: res.success });

        setTimeout(() => {
          setCreatedItemToast(null);
        }, 4000);
      }
    };

    fetchCreatedProduct();

    setName("");
    setPrice("");
    setIsAdding(false);
  };

  const deleteItem = async (id: number) => {
    if (!token || !openCategory) return;

    const fetchDeletedProduct = async () => {
      const res = await deleteItemApi.del(`${id}`);

      if (res) {
        const toRemoveProduct = res.data;
        setItems((prev) => ({
          ...prev,
          [openCategory]: prev[openCategory].filter(
            (prod) => prod.id !== toRemoveProduct.id
          ),
        }));

        setRemovedItemToast({ message: res.message, success: res.success });

        setTimeout(() => {
          setRemovedItemToast(null);
        }, 4000);
      }
    };

    fetchDeletedProduct();
  };

  useEffect(() => {
      if (!token) return;
  
      const fetchProducts = async () => {
        const res = await itemsApi.get();
  
        if (res) {
          setItems(res.data);
        }
      };
  
      fetchProducts();
    }, [token]);

  const showAddModal = () => {
    return (
      <>
        <button onClick={() => setIsAdding(false)}>← Back</button>

        <h2>Add new item</h2>

        <form
          onSubmit={async (e) => {
            e.preventDefault();
            await saveMethod();
          }}
        >
          <input
            type="text"
            placeholder="Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />

          <input
            type="number"
            placeholder="Price"
            value={price}
            onChange={(e) => setPrice(Number(e.target.value))}
          />

          <button type="submit">Save</button>
        </form>
      </>
    );
  };

  const saveEdit = async (id: number) => {
    if (!token || !openCategory || !editName || price == null) return;

    const body = {
      name: editName,
      price: editPrice,
      category: openCategory,
    };

    const fetchUpdatedProduct = async () => {
      const res = await updateItemApi.post(`?itemId=${id}`, body);

      if (res) {
        const toUpdateProduct = res.data;
        setItems((prev) => ({
          ...prev,
          [openCategory]: prev[openCategory].map((prod) =>
            prod.id === toUpdateProduct.id ? toUpdateProduct : prod
          ),
        }));
        setEditedItemToast({ message: res.message, success: res.success });

        setTimeout(() => {
          setEditedItemToast(null);
        }, 4000);
      }
    };

    fetchUpdatedProduct();

    setEditName("");
    setEditPrice("");
    setEditingProductId(null);
  };

  const itemsModal = (openCategory: string) => {
    return (
      <>
        <input
          type="button"
          value="Add item"
          onClick={() => setIsAdding(true)}
        />

        <h2>{openCategory}</h2>

        {items[openCategory].map((product) =>
          editingProductId !== product.id ? (
            <p key={product.id}>
              {product.name} - {product.price} MKD
              <input
                type="button"
                value="delete"
                onClick={async () => {
                  await deleteItem(product.id);
                }}
              />
              <input
                type="button"
                value="edit"
                onClick={() => {
                  setEditingProductId(product.id);
                  setEditName(product.name);
                  setEditPrice(product.price);
                }}
              />
            </p>
          ) : (
            <p key={product.id}>
              <input
                type="text"
                value={editName}
                onChange={(e) => setEditName(e.target.value)}
              />
              <input
                type="number"
                value={editPrice}
                onChange={(e) => setEditPrice(Number(e.target.value))}
              />
              <button onClick={() => saveEdit(product.id)}>Save</button>
              <button onClick={() => setEditingProductId(null)}>Cancel</button>
            </p>
          )
        )}

        <button
          onClick={() => {
            setOpenCategory(null);
            setIsAdding(false);
          }}
        >
          Close
        </button>
      </>
    );
  };

  return (
    <div style={{ width: "50%" }}>
      {Object.entries(items).map(([category, products]) => (
        <div className={style.items_category_preview} key={category}>
          <h2>{category}</h2>

          {products.slice(0, 1).map((product) => (
            <p key={product.id}>
              {product.name} - {product.price} MKD
            </p>
          ))}

          {products.length > 1 && (
            <button onClick={() => setOpenCategory(category)}>Show more</button>
          )}
        </div>
      ))}

      {openCategory && (
        <div className={style.modal_overlay}>
          <div className={style.modal}>
            {!isAdding ? itemsModal(openCategory) : showAddModal()}
          </div>
        </div>
      )}
      {createdItemToast && (
        <div
          className={`toast ${
            createdItemToast.success ? "success-toast" : "fail-toast"
          }`}
        >
          {createdItemToast.message}
        </div>
      )}
      {removedItemToast && (
        <div
          className={`toast ${
            removedItemToast.success ? "success-toast" : "fail-toast"
          }`}
        >
          {removedItemToast.message}
        </div>
      )}
      {editedItemToast && (
        <div
          className={`toast ${
            editedItemToast.success ? "success-toast" : "fail-toast"
          }`}
        >
          {editedItemToast.message}
        </div>
      )}
    </div>
  );
};

export default ItemsDisplay;
