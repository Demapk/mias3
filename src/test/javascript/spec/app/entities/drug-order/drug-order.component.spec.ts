/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MiasTestModule } from '../../../test.module';
import { DrugOrderComponent } from '../../../../../../main/webapp/app/entities/drug-order/drug-order.component';
import { DrugOrderService } from '../../../../../../main/webapp/app/entities/drug-order/drug-order.service';
import { DrugOrder } from '../../../../../../main/webapp/app/entities/drug-order/drug-order.model';

describe('Component Tests', () => {

    describe('DrugOrder Management Component', () => {
        let comp: DrugOrderComponent;
        let fixture: ComponentFixture<DrugOrderComponent>;
        let service: DrugOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [DrugOrderComponent],
                providers: [
                    DrugOrderService
                ]
            })
            .overrideTemplate(DrugOrderComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DrugOrderComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DrugOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DrugOrder(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.drugOrders[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
