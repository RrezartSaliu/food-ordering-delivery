import { useState, useCallback } from "react";
import axios from "axios";
import type { AxiosRequestConfig } from "axios";
import type { ApiResponse } from "../types/ApiResponse";

interface UseApiReturn<T> {
  data: ApiResponse<T> | null;
  error: string | null;
  loading: boolean;
  get: (
    path?: string,
    config?: AxiosRequestConfig
  ) => Promise<ApiResponse<T> | null>;
  post: (
    path: string,
    body: any,
    config?: AxiosRequestConfig
  ) => Promise<ApiResponse<T> | null>;
  put: (
    path: string,
    body: any,
    config?: AxiosRequestConfig
  ) => Promise<ApiResponse<T> | null>;
  del: (
    path: string,
    config?: AxiosRequestConfig
  ) => Promise<ApiResponse<T> | null>;
}

export function useApi<T = any>(
  baseUrl: string,
  token?: string
): UseApiReturn<T> {
  const [data, setData] = useState<ApiResponse<T> | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(false);

  const axiosConfig = (
    extraConfig?: AxiosRequestConfig
  ): AxiosRequestConfig => ({
    headers: {
      Authorization: token ? `Bearer ${token}` : undefined,
      "Content-Type": "application/json",
    },
    ...extraConfig,
  });

  const get = useCallback(
    async (path = "", config?: AxiosRequestConfig) => {
      setLoading(true);
      setError(null);
      try {
        const res = await axios.get<ApiResponse<T>>(
          `${baseUrl}${path}`,
          axiosConfig(config)
        );
        setData(res.data);
        return res.data;
      } catch (err: any) {
        setError(err.response?.data?.message || err.message);
        return null;
      } finally {
        setLoading(false);
      }
    },
    [baseUrl, token]
  );

  const post = useCallback(
    async (path: string, body: any, config?: AxiosRequestConfig) => {
      setLoading(true);
      setError(null);
      try {
        const res = await axios.post<ApiResponse<T>>(
          `${baseUrl}${path}`,
          body,
          axiosConfig(config)
        );
        setData(res.data);
        return res.data;
      } catch (err: any) {
        setError(err.response?.data?.message || err.message);
        return null;
      } finally {
        setLoading(false);
      }
    },
    [baseUrl, token]
  );

  const put = useCallback(
    async (path: string, body: any, config?: AxiosRequestConfig) => {
      setLoading(true);
      setError(null);
      try {
        const res = await axios.put<ApiResponse<T>>(
          `${baseUrl}${path}`,
          body,
          axiosConfig(config)
        );
        setData(res.data);
        return res.data;
      } catch (err: any) {
        setError(err.response?.data?.message || err.message);
        return null;
      } finally {
        setLoading(false);
      }
    },
    [baseUrl, token]
  );

  const del = useCallback(
    async (path: string, config?: AxiosRequestConfig) => {
      setLoading(true);
      setError(null);
      try {
        const res = await axios.delete<ApiResponse<T>>(
          `${baseUrl}${path}`,
          axiosConfig(config)
        );
        setData(res.data);
        return res.data;
      } catch (err: any) {
        setError(err.response?.data?.message || err.message);
        return null;
      } finally {
        setLoading(false);
      }
    },
    [baseUrl, token]
  );

  return { data, error, loading, get, post, put, del };
}
