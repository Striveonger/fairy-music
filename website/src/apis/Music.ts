import { Result, http } from "./index";
import { SearchItem, Play, BilibiliPlay } from "../types";

export async function search(keyword: string, page: number): Promise<SearchItem[]> {
    const result = await http.get<Result<SearchItem>>("/api/v1/fairy/music/search", { params: { keyword, page } })
                        .then((response: { data: Result<SearchItem> }) => response.data);
    return result.data as SearchItem[];
}

export async function playlist(url: string): Promise<Play[]> {
    const result = await http.get<Result<Play>>("/api/v1/fairy/music/playlist", { params: { url } })
                        .then((response: { data: Result<Play> }) => response.data);
    return result.data as Play[];
}