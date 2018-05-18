import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Prescription } from './prescription.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Prescription>;

@Injectable()
export class PrescriptionService {

    private resourceUrl =  SERVER_API_URL + 'api/prescriptions';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(prescription: Prescription): Observable<EntityResponseType> {
        const copy = this.convert(prescription);
        return this.http.post<Prescription>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(prescription: Prescription): Observable<EntityResponseType> {
        const copy = this.convert(prescription);
        return this.http.put<Prescription>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Prescription>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Prescription[]>> {
        const options = createRequestOption(req);
        return this.http.get<Prescription[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Prescription[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Prescription = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Prescription[]>): HttpResponse<Prescription[]> {
        const jsonResponse: Prescription[] = res.body;
        const body: Prescription[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Prescription.
     */
    private convertItemFromServer(prescription: Prescription): Prescription {
        const copy: Prescription = Object.assign({}, prescription);
        copy.date = this.dateUtils
            .convertDateTimeFromServer(prescription.date);
        return copy;
    }

    /**
     * Convert a Prescription to a JSON which can be sent to the server.
     */
    private convert(prescription: Prescription): Prescription {
        const copy: Prescription = Object.assign({}, prescription);

        copy.date = this.dateUtils.toDate(prescription.date);
        return copy;
    }
}
