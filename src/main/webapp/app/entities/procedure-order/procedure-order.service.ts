import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ProcedureOrder } from './procedure-order.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ProcedureOrder>;

@Injectable()
export class ProcedureOrderService {

    private resourceUrl =  SERVER_API_URL + 'api/procedure-orders';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(procedureOrder: ProcedureOrder): Observable<EntityResponseType> {
        const copy = this.convert(procedureOrder);
        return this.http.post<ProcedureOrder>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(procedureOrder: ProcedureOrder): Observable<EntityResponseType> {
        const copy = this.convert(procedureOrder);
        return this.http.put<ProcedureOrder>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ProcedureOrder>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ProcedureOrder[]>> {
        const options = createRequestOption(req);
        return this.http.get<ProcedureOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ProcedureOrder[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ProcedureOrder = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ProcedureOrder[]>): HttpResponse<ProcedureOrder[]> {
        const jsonResponse: ProcedureOrder[] = res.body;
        const body: ProcedureOrder[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ProcedureOrder.
     */
    private convertItemFromServer(procedureOrder: ProcedureOrder): ProcedureOrder {
        const copy: ProcedureOrder = Object.assign({}, procedureOrder);
        copy.date = this.dateUtils
            .convertDateTimeFromServer(procedureOrder.date);
        return copy;
    }

    /**
     * Convert a ProcedureOrder to a JSON which can be sent to the server.
     */
    private convert(procedureOrder: ProcedureOrder): ProcedureOrder {
        const copy: ProcedureOrder = Object.assign({}, procedureOrder);

        copy.date = this.dateUtils.toDate(procedureOrder.date);
        return copy;
    }
}
