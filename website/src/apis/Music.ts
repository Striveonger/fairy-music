import { Result, http } from "./index";
import { SearchItem } from "../types";

export async function search(keyword: string, page: number): Promise<SearchItem[]> {
    const result = await http.get<Result<SearchItem>>("/api/v1/fairy/music/search", { params: { keyword, page } })
                        .then((response: { data: Result<SearchItem> }) => response.data);
    return result.data as SearchItem[];
}
