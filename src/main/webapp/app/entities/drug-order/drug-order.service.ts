import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DrugOrder } from './drug-order.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DrugOrder>;

@Injectable()
export class DrugOrderService {

    private resourceUrl =  SERVER_API_URL + 'api/drug-orders';

    constructor(private http: HttpClient) { }

    create(drugOrder: DrugOrder): Observable<EntityResponseType> {
        const copy = this.convert(drugOrder);
        return this.http.post<DrugOrder>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(drugOrder: DrugOrder): Observable<EntityResponseType> {
        const copy = this.convert(drugOrder);
        return this.http.put<DrugOrder>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DrugOrder>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DrugOrder[]>> {
        const options = createRequestOption(req);
        return this.http.get<DrugOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DrugOrder[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DrugOrder = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DrugOrder[]>): HttpResponse<DrugOrder[]> {
        const jsonResponse: DrugOrder[] = res.body;
        const body: DrugOrder[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DrugOrder.
     */
    private convertItemFromServer(drugOrder: DrugOrder): DrugOrder {
        const copy: DrugOrder = Object.assign({}, drugOrder);
        return copy;
    }

    /**
     * Convert a DrugOrder to a JSON which can be sent to the server.
     */
    private convert(drugOrder: DrugOrder): DrugOrder {
        const copy: DrugOrder = Object.assign({}, drugOrder);
        return copy;
    }
}
