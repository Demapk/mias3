import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Diseas } from './diseas.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Diseas>;

@Injectable()
export class DiseasService {

    private resourceUrl =  SERVER_API_URL + 'api/diseas';

    constructor(private http: HttpClient) { }

    create(diseas: Diseas): Observable<EntityResponseType> {
        const copy = this.convert(diseas);
        return this.http.post<Diseas>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(diseas: Diseas): Observable<EntityResponseType> {
        const copy = this.convert(diseas);
        return this.http.put<Diseas>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Diseas>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Diseas[]>> {
        const options = createRequestOption(req);
        return this.http.get<Diseas[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Diseas[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Diseas = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Diseas[]>): HttpResponse<Diseas[]> {
        const jsonResponse: Diseas[] = res.body;
        const body: Diseas[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Diseas.
     */
    private convertItemFromServer(diseas: Diseas): Diseas {
        const copy: Diseas = Object.assign({}, diseas);
        return copy;
    }

    /**
     * Convert a Diseas to a JSON which can be sent to the server.
     */
    private convert(diseas: Diseas): Diseas {
        const copy: Diseas = Object.assign({}, diseas);
        return copy;
    }
}
