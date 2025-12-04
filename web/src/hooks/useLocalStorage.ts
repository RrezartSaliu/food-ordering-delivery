import { useState, useEffect } from "react";

export const useLocalStorage = (key: string, initialValue: string) => {
    const [value, setValue] = useState(() => {
        const saved = localStorage.getItem(key);
        return saved ?? initialValue;
    });

    useEffect(() => {
        localStorage.setItem(key, value);
    }, [key, value]);

    return [value, setValue] as const;
};
