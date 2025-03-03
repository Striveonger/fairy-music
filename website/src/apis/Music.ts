import { Result, http } from "./index";
import { SearchItem, Play, BilibiliPlay } from "../types";

export async function search(keyword: string, page: number): Promise<SearchItem[]> {
    const result = await http.get<Result<SearchItem>>("/api/v1/fairy/music/search", { params: { keyword, page } });
    return result.data.data as SearchItem[];
}

export async function playlist(url: string): Promise<Play[]> {
    // http.get<Result<Play>>("/api/v1/fairy/music/playlist", { params: { url } }).then(x).catch(y);
    const response = await http.get<Result<Play>>("/api/v1/fairy/music/playlist", { params: { url } });
    return response.data.data as Play[];
}